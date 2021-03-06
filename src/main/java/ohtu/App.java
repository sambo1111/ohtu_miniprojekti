package ohtu;

import ohtu.io.ConsoleIO;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.*;
import ohtu.domain.Blog;
import ohtu.domain.Podcast;
import ohtu.domain.Suggestable;
import ohtu.domain.Suggestion;
import ohtu.domain.Tag;
import ohtu.domain.Type;
import ohtu.io.IO;
import ohtu.services.SuggestionService;

public class App {

    private IO io;
    private SuggestionService sugg;
    private Validator validator;

    public App(IO io, SuggestionService sugg) {
        this.io = io;
        this.sugg = sugg;
        this.validator = new Validator();
    }

    public void run() {
        io.print("Welcome!");
        while (true) {
            String command = io.readLine("\nCommand (list, find, remove, add or edit empty command exits program):");
            if (command.isEmpty()) {
                break;
            }
            if (command.equals("list")) {
                list(sugg.listAllSuggestions(), false);
            } else if (command.equals("add")) {
                add();
            } else if (command.equals("find")) {
                find();
            } else if (command.equals("remove")) {
                remove();
            } else if (command.equals("edit")) {
                edit();
            } else {
                io.print("Unknown command!");
            }
        }

    }

    public void edit() {
        String input = io.readLine("\nSearch suggestions to edit (type y, otherwise press enter to list all)");
        List<Suggestion> suggestions = null;

        if (input.equals("y")) {
            String arg = io.readLine("Enter keyword:");
            suggestions = sugg.findByAll(arg);
        } else {
            suggestions = sugg.listAllSuggestions();
        }

        if (suggestions.size() > 0) {

            list(suggestions, true);

            input = io.readLine("\nChoose suggestion to edit (type the number):");

            if (input.matches("\\d+")) {
                int index = Integer.parseInt(input);

                if (index >= 0 && index < suggestions.size()) {
                    io.print("\nEditing following suggestion:");
                    Suggestion s = suggestions.get(index);
                    io.print(s.toString());

                    io.print("\nSelect one:");
                    input = io.readLine(
                            "1.: edit attribute"
                            + "\n2.: edit tags");

                    if (input.equals("1")) {

                        editSuggestable(s.getSuggestable());

                    } else if (input.equals("2")) {
                        io.print("\nDo you want to edit existing tags or add new tags? Select one: ");
                        input = io.readLine("\n1.: edit existing tag\n2.: add new tags");
                        if (input.equals("1")) {
                            editTag(s.getTags());
                            io.print("The tag has been changed!");
                        } else if (input.equals("2")) {
                            UserReader ur = new UserReader(io);
                            List<Tag> tags = new ArrayList<>();
                            tags = ur.readAndCreateTags();
                            sugg.addTagsForSuggestion(s.getId(), tags);
                            io.print("New tag(s) added!");
                        } else {
                            io.print("Incorrect index given!");
                        }
                    }
                } else {
                    io.print("Incorrect index given!");
                }
            }
        }
    }

    private void editTag(List<Tag> tags){
        io.print("\nChoose tag to edit:");
        for (int i = 0; i < tags.size(); i++) {
            io.print(i + ".:" + tags.get(i).getName());
        }
        String input = io.readLine("");
        if (input.matches("\\d+")) {
            int index = Integer.parseInt(input);
            if (index >= 0 && index < tags.size()) {
                Tag t = tags.get(index);
                input = io.readLine("\nEnter new content:");
                sugg.editTag(t, input);
            } else {
                io.print("Incorrect index given!");
            }
        }
    }

    private void editSuggestable(Suggestable s) {
        String input = io.readLine("\nEnter name of field to edit (e.g. title):");
        if (input.toLowerCase().equals("title")) {
            String newValue = enterNewFieldValue("title");
            s.setTitle(newValue);
            sugg.updateSuggestable(s);
            io.print("Field successfully updated!");
        } else if (input.toLowerCase().equals("author")) {
            String newValue = enterNewFieldValue("author");
            s.setCreator(newValue);
            sugg.updateSuggestable(s);
            io.print("Field successfully updated!");
        } else if (input.toLowerCase().equals("description")) {
            String newValue = enterNewFieldValue("description");
            s.setDescription(newValue);
            sugg.updateSuggestable(s);
            io.print("Field successfully updated!");
        } else if (input.toLowerCase().equals("isbn")) {
            if (s.getType().equals(Type.BOOK.toString())) {
                io.print("Editing ISBN not supported.");
            } else {
                io.print("Incorrect field name.");
            }

        } else if (input.toLowerCase().equals("url")) {
            if (!s.getType().equals(Type.BOOK.toString())) {
                io.print("Editing URL not supported.");
            } else {
                io.print("Incorrect field name.");
            }
        } else if (input.toLowerCase().equals("blog name")) {
            if (s.getType().equals(Type.BLOG.toString())) {
                String newValue = enterNewFieldValue("blog name");
                Blog b = (Blog) s;
                b.setBlogName(newValue);
                s = (Suggestable) b;
                sugg.updateSuggestable(s);
                io.print("Field successfully updated!");
            } else {
                io.print("Incorrect field name.");
            }
        } else if (input.toLowerCase().equals("podcast name")) {
            if (s.getType().equals(Type.PODCAST.toString())) {
                String newValue = enterNewFieldValue("podcast name");
                Podcast b = (Podcast) s;
                b.setPodcastName(newValue);
                s = (Suggestable) b;
                sugg.updateSuggestable(s);
                io.print("Field successfully updated!");
            } else {
                io.print("Incorrect field name.");
            }

        } else {
            io.print("Incorrect field name.");
        }

    }

    private String enterNewFieldValue(String fieldName) {
        return io.readLine("\nNew content for field " + fieldName + ": ");
    }

    
    public void remove() {
        String ans = io.readLine("\nSearch suggestions to remove (type y, otherwise press enter)");

        List<Suggestion> suggestions = null;

        if (ans.equals("y")) {
            String arg = io.readLine("Enter keyword:");
            suggestions = sugg.findByAll(arg);
        } else {
            suggestions = sugg.listAllSuggestions();
        }

        if (suggestions.size() > 0) {
            list(suggestions, true);

            io.print("\nChoose suggestion to remove:");
            String input = io.readLine("");

            if (input.matches("\\d+")) {
                int index = Integer.parseInt(input);

                if (index >= 0 && index < suggestions.size()) {
                    String confirm = io.readLine("Are you sure? (type y)");
                    if (confirm.equals("y")) {
                        sugg.removeSuggestion(suggestions.get(index));
                        io.print("Suggestion removed!");
                    } else {
                        io.print("Suggestion not removed!");
                    }
                    return;
                }
            }
            io.print("Incorrect index given!");
        }
    }

    public void add() {
        String command = io.readLine("What would you like to add? (types: book, blog, video, podcast)");
        if (command.equals("book")) {
            add(Type.BOOK);
        } else if (command.equals("blog")) {
            add(Type.BLOG);
        } else if (command.equals("video")) {
            add(Type.VIDEO);
        } else if (command.equals("podcast")) {
            add(Type.PODCAST);
        } else {
            io.print("Unknown command!");
        }
    }

    public void add(Type t) {
        UserReader ur = new UserReader(io);
        String key = ur.readKey(t);
        Suggestable s = null;

        switch (t) {
            case BOOK:
                s = sugg.findBookByISBN(key);
                break;
            case BLOG:
                s = sugg.findBlogByURL(key);
                break;
            case VIDEO:
                s = sugg.findVideoByURL(key);
                break;
            case PODCAST:
                s = sugg.findPodcastByURL(key);
                break;
        }

        List<Tag> tags = new ArrayList<>();

        if (s == null) {
            s = ur.readAndCreateSuggestable(t, key);
            sugg.addSuggestable(s);
            tags = ur.readAndCreateTags();

        } else {
            io.print("\nFound the following " + t.toString().toLowerCase() + ":");
            io.print(s.toString());
            s = null;   //ettei saa laittaa samalle teokselle useita testejä
        }

        if (sugg.addSuggestion(s, tags)) {
            io.print("New suggestion with " + t.toString().toLowerCase() + " added!");
        } else {
            io.print("Failed to add suggestion with " + t.toString().toLowerCase() + "!");
        }

    }

    public void list(List<Suggestion> suggestions, boolean showIndexes) {
        if (suggestions.isEmpty()) {
            io.print("\nNo suggestions found.");
        } else {
            for (int i = 0; i < suggestions.size(); i++) {
                if (showIndexes) {
                    io.print("\n" + i + ".:\n" + suggestions.get(i));
                } else {
                    io.print("\n" + suggestions.get(i));
                }
            }
        }
    }

    public void find() {
        List<Suggestion> suggestions_found = new ArrayList();
        String command;
        while (true) {
            command = io.readLine("Find by (any, q = back): ");
            if (command.equals("any")) {
                String command_any = io.readLine("Keyword: ");
                List<Suggestion> suggestionsByAll = sugg.findByAll(command_any);
                if (!suggestionsByAll.isEmpty()) {
                    suggestions_found.addAll(suggestionsByAll);
                }
                break;
            } else if (command.equals("q")) {
                break;
            } else {
                io.print("Unknown command!");
            }
        }

        if (!command.equals("q")) {
            if (!suggestions_found.isEmpty()) {
                io.print("\nFound the following suggestions: ");
                list(suggestions_found, false);

            } else {
                io.print("No suggestions found.");
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Database database = new Database("jdbc:sqlite:database.db");

        InterfaceBookDao bookDao = new SQLBookDao(database);
        InterfaceBlogDao blogDao = new SQLBlogDao(database);
        InterfaceVideoDao videoDao = new SQLVideoDao(database);
        InterfacePodcastDao podcastDao = new SQLPodcastDao(database);
        InterfaceTagDao tagDao = new SQLTagDao(database);
        InterfaceSuggestionDao suggestionDao = new SQLSuggestionDao(database, bookDao, blogDao, podcastDao, videoDao, tagDao);
        SuggestionService sugg = new SuggestionService(suggestionDao, bookDao, blogDao, podcastDao, videoDao, tagDao);

        // Tässä kommentoituna mahdollisuus kutsua esimerkkidatan lisäämistä.
//        if (sugg.listAllSuggestions().isEmpty()) {
//            sugg.fillWithExampleData();
//        }

        IO io = new ConsoleIO();
        new App(io, sugg).run();
    }

}
