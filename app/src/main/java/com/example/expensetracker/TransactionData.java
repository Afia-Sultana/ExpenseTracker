package com.example.expensetracker;

public class TransactionData {
    private int id;
    private String date;
    private String amount;
    private String catagory;
    public TransactionData(int i,String am,String cat,String d)
    {
        this.id=i;
        this.amount=am;
        this.catagory=cat;
        this.date=d;

    }
    public TransactionData(String amount,String catagory)
    {
        this.amount=amount;
        this.catagory=catagory;
    }
    public int getId()
    {
        return this.id;
    }
    public String getamount()
    {
        return this.amount;
    }
    public String getcatagory()
    {
        return this.catagory;
    }
    public String getdate()
    {
        return this.date;
    }

}
