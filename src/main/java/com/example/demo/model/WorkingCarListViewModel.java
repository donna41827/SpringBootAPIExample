package com.example.demo.model;

import java.util.List;
import lombok.*;

@Data
public class WorkingCarListViewModel
{
    public int Role ;
    public Principal Principal ;
    public List<CarList> CarList ;
}