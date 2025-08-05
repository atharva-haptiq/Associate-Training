package service;

import generics.Message;
import entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserService<T> {

    Set<User> userSet = new TreeSet<>();
    Map<User, List<Message<T>>> messageHistory = new HashMap<>();

    public void joinChat(String username){
        System.out.println(username.toUpperCase()+" has joined a chat");
        userSet.add(new User(username));
    }


    public void sendMessage (Message<T> message){
        messageHistory.computeIfAbsent(message.getSender(), k -> new ArrayList<>()).add(message);
    }

    public List<Message<T>> getMessages(String from , String to){
        List<Message<T>> pendingMessages = messageHistory
                .values().
                stream()
                .flatMap(List::stream)
                .filter(message -> message.getReceiver().getUsername().equals(to) && message.getSender().getUsername().equals(from))
                .collect(Collectors.toList());

        System.out.println(pendingMessages);
        if (!pendingMessages.isEmpty()) return pendingMessages;
        else return null;
    }

    public Set<User> getActiveUsers(){
        return userSet;
    }



}
