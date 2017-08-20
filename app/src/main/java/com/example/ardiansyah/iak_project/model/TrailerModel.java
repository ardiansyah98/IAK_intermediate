package com.example.ardiansyah.iak_project.model;

import java.util.List;

/**
 * Created by Ardiansyah on 08/08/2017.
 */

public class TrailerModel
{
    public int id;
    public List<Result> results;

    public class Result
    {
        public String id;
        public String iso_639_1;
        public String iso_3166_1;
        public String key;
        public String name;
        public String site;
        public int size;
        public String type;
    }
}
