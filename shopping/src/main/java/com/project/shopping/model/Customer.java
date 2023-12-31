package com.project.shopping.model;

import java.util.ArrayList;

public class Customer{
    private int ID;
    private String email, phone, name, password;
    private double balance;
    private static int counter = 0;
    private ArrayList<IChannel> myChannels;

    public  Customer(){
        ID = counter++;
    }
    public String getDetails(){
        return " Customer Details : \n"+"ID"+ID+"Name : "+name+"\n"+"Phone: "+phone+"\n"+"Email: "+email+"\n"+"balance : "+balance;
    }
    public Customer(String Email , String Phone , String Name , double balance, ArrayList<IChannel> myChannels){
        ID=counter;
        counter++;
        this.email=Email;
        this.phone=Phone;
        this.name=Name;
        this.balance=balance;
        this.myChannels = myChannels;
    }

    public int getID() {
        return ID;
    }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setID(int ID) { this.ID = ID; }

    public void setPhone(String phone){ this.phone = phone; }

    public void setEmail(String email) { this.email = email; }

    public void setBalance(double balance) { this.balance = balance; }

    public String getPassword() { return password; }

    public double getBalance() { return balance; }

    public ArrayList<IChannel> getMyChannels() {
        return myChannels;
    }

    public void setMyChannels(ArrayList<IChannel> myChannels) {
        this.myChannels = myChannels;
    }
    public void addChannel(IChannel channel){
        this.myChannels.add(channel) ;
    }
}
