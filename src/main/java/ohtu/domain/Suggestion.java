/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hasasami
 */
public class Suggestion {
    
    // private List<Tag> tags;
    //private List<Comment> comments;
    //private List<Courses> rcourses;
    //private List<Courses> esitietoKurssit;
    private Suggestable suggestable;
    private int id;
    private String type;
    
//    public Suggestion(Suggestable suggestable) {
//        //this.tags.addAll(Arrays.asList(tags));
//        this.suggestable = suggestable;
//        
//    }
    public Suggestion(Suggestable suggestable, String type) {
        this.suggestable = suggestable;
        this.id = id;
        this.type = type;
    }
    //merkki
//    public void addTags(Tag... tags) {
//        this.tags.addAll(Arrays.asList(tags));
//    }
//
//    public List<Tag> getTags() {
//        return tags;
//    }
    //merkki
    public Suggestable getSuggestable() {
        return this.suggestable;
    }
    
    public String getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
//        return suggestable.toString() + "\nType: " + suggestable.getType();
        return suggestable.toString() + "\nType: " + this.type;
    }
    
}
