package com.example.ardiansyah.iak_project.model;

import java.util.List;

/**
 * Created by Ardiansyah on 08/08/2017.
 */

public class ReviewModel
{
    public int id;
    public int page;
    public List<Result> results;
    public int total_pages;
    public int total_results;

    public class Result
    {
        public String id;
        public String author;
        public String content;
        public String url;
    }
}

