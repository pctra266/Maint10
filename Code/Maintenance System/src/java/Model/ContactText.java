/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class ContactText {
    private int id;
    private String title;
    private String subtitle;

    public ContactText() {}

    public ContactText(int id, String title, String subtitle) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public String toString() {
        return "ContactTexts{" + "id=" + id + ", title=" + title + ", subtitle=" + subtitle + '}';
    }

    
}

