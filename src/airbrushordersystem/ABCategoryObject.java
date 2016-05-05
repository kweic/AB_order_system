/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airbrushordersystem;

/**
 *
 * @author kw
 */
public class ABCategoryObject {
    private String category;
    private String catPath;
    
    public ABCategoryObject(String category, String catPath){
        this.category = category;
        this.catPath = catPath;
    }
    
    public String getCategory(){
        return category;
    }
    
    public String getPath(){
        return catPath;
    }
    
}
