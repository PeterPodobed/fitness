package by.it_academy.fitness.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDto<P> {

    @JsonProperty("number")
    private int number;

    @JsonProperty("size")
    private int size;

    @JsonProperty("total_pages")
    private int total_pages;

    @JsonProperty("total_elements")
    private long total_elements;

    @JsonProperty("first")
    private boolean first;

    @JsonProperty("number_of_elements")
    private int number_of_elements;

    @JsonProperty("last")
    private boolean last;

    @JsonProperty("content")
    private List<P> content;

    public PageDto(int number, int size, int total_pages, long total_elements,
                   boolean first, int number_of_elements, boolean last, List<P> content) {
        this.number = number;
        this.size = size;
        this.total_pages = total_pages;
        this.total_elements = total_elements;
        this.first = first;
        this.number_of_elements = number_of_elements;
        this.last = last;
        this.content = content;
    }

    public PageDto() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public long getTotal_elements() {
        return total_elements;
    }

    public void setTotal_elements(long total_elements) {
        this.total_elements = total_elements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumber_of_elements() {
        return number_of_elements;
    }

    public void setNumber_of_elements(int number_of_elements) {
        this.number_of_elements = number_of_elements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<P> getContent() {
        return content;
    }

    public void setContent(List<P> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "number=" + number +
                ", size=" + size +
                ", total_pages=" + total_pages +
                ", total_elements=" + total_elements +
                ", first=" + first +
                ", number_of_elements=" + number_of_elements +
                ", last=" + last +
                ", content=" + content +
                '}';
    }
}
