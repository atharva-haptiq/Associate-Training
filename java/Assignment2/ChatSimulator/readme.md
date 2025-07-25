
# Chat Simulator App

## Overview

This is a simple **Chat Simulator** application written in Java. It allows multiple users to join a chat room, send messages to each other, and view message history and active users. The app uses generic message handling and stores messages along with user information.

---

## Project Structure

- **entity.User** — Represents a chat user with a username. Implements `Comparable` for sorting users alphabetically, ignoring case.
- **generics.Message<T>** — Generic message class encapsulating sender, receiver, content, and timestamp.
- **service.UserService<T>** — Manages users, sending messages, and retrieving message history.
- **Main** — Console-based user interface to interact with the chat system.

---

## Class Details and Logic

### 1. `User` Class

- Contains a single field: `username`.
- Implements `Comparable<User>` to sort users alphabetically ignoring case.
- Overrides `equals` to treat usernames case-insensitively.

### 2. `Message<T>` Class

- Generic to support messages of any content type.
- Stores:
    - `sender` (User object)
    - `receiver` (User object)
    - `content` (generic type T)
    - `timestamp` (message creation time)
- Implements `Comparable` based on timestamp for chronological ordering.
- Overrides `toString()` to print messages with timestamp and sender info.

### 3. `UserService<T>` Class

- Maintains:
    - `userSet`: A `TreeSet<User>` to keep track of active users in sorted order.
    - `messageHistory`: A `HashMap<User, List<Message<T>>>` mapping senders to their sent messages.
- Core Methods:
    - `joinChat(String username)`: Adds a new user to the active users set.
    - `sendMessage(Message<T> message)`: Adds the message to the sender’s message history.
    - `getMessages(String from, String to)`: Retrieves messages sent from one user to another by filtering through all messages.
    - `getActiveUsers()`: Returns the current active user set.

### 4. `Main` Class (User Interface)

- Provides a console menu to interact with the chat:
    1. **Join Chat**: User enters a username to join.
    2. **Send Message**: Enter sender username, receiver username, and message content.
    3. **View Messages**: Check messages received from a specific sender.
    4. **View Active Users**: Display all users currently in chat.
    5. **Exit**: Terminate the application.

- Uses `Scanner` for input and loops infinitely until the user chooses to exit.

---

## How It Works (Workflow)

1. **Joining**: User enters a username to join. They are added to the `userSet`.
2. **Sending Messages**:
    - User inputs sender, receiver, and message.
    - A new `Message` object is created with current timestamp.
    - The message is stored in `messageHistory` under the sender’s key.
3. **Retrieving Messages**:
    - User specifies the recipient (themselves) and the sender whose messages they want to see.
    - The system filters messages from the sender to the receiver and displays them.
4. **Listing Users**:
    - Displays all active users sorted alphabetically.
5. **Exit**: Ends the program.

---

## Notes

- Users are uniquely identified by their username (case-insensitive).
- Messages are stored by sender but retrieved by filtering sender/receiver pairs.
- Timestamp helps to keep message order.
- The system currently does not remove users or messages.
- Message content is generic (`T`), allowing extension to any content type (String in this app).

---

## How to Run

1. Compile all classes (`User`, `Message`, `UserService`, and `Main`).
2. Run the `Main` class.
3. Use the console interface to join chat, send/view messages, and list users.

---

## Example Interaction

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 1
Enter username to join chat: Atharva
ATHARVA has joined the chat.

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 1
Enter username to join chat: Ak
AK has joined the chat.

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 2
Enter from's username: Atharva
Enter recipient's username: Ak
Enter message: Hello
Message sent!

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 3
Enter your username to check messages: Ak
Enter the recipient's name to check messages for: Atharva


Messages received:
From Atharva | Time: 2025-07-22T19:06:29.664
Message: Hello

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 2
Enter from's username: Ak
Enter recipient's username: Atharva
Enter message: Hello from Ak
Message sent!

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 3
Enter your username to check messages: Atharva
Enter the recipient's name to check messages for: Ak


Messages received:
From Ak | Time: 2025-07-22T19:07:00.180
Message: Hello from Ak

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 1
Enter username to join chat: H1
H1 has joined the chat.

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 1
Enter username to join chat: H2
H2 has joined the chat.

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 1
Enter username to join chat: H3
H3 has joined the chat.

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 4
Active Users:
- Ak
- Atharva
- H1
- H2
- H3

                     Chat Out                     

1. Join Chat
2. Send Message
3. View Messages
4. View Active Users
5. Exit
------------------------------------------
Enter your choice: 5
Exiting chat. Goodbye!

Process finished with exit code 0


