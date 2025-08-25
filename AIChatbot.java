import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class AIChatbot extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private HashMap<String, ArrayList<String>> responses;
    private ArrayList<String> defaultResponses;
    private int messageCount = 0;
    private ScriptEngine mathEngine;
    private DateTimeFormatter timeFormatter;
    
    public AIChatbot() {
        super("My AI Chatbot");
        initializeResponses();
        setupMathEngine();
        createGUI();
    }
    
    private void setupMathEngine() {
        ScriptEngineManager manager = new ScriptEngineManager();
        mathEngine = manager.getEngineByName("JavaScript");
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }
    
    private void initializeResponses() {
        responses = new HashMap<>();
        defaultResponses = new ArrayList<>();
        
        // Greeting patterns and responses
        ArrayList<String> greetings = new ArrayList<>();
        greetings.add("Hello! How can I help you today?");
        greetings.add("Hi there! What can I do for you?");
        greetings.add("Hey! Nice to meet you.");
        responses.put("(hi|hello|hey|greetings|good morning|good afternoon)", greetings);
        
        // How are you patterns
        ArrayList<String> howAreYou = new ArrayList<>();
        howAreYou.add("I'm just a program, but I'm functioning well. Thanks for asking!");
        howAreYou.add("I don't have feelings, but my code is running smoothly. How about you?");
        responses.put("(how are you|how's it going|how do you feel)", howAreYou);
        
        // Name patterns
        ArrayList<String> nameResponses = new ArrayList<>();
        nameResponses.add("I'm ChatBot, your virtual assistant.");
        nameResponses.add("You can call me ChatBot.");
        responses.put("(what is your name|who are you|introduce yourself)", nameResponses);
        
        // Help patterns
        ArrayList<String> helpResponses = new ArrayList<>();
        helpResponses.add("I can answer questions, have conversations, or just chat. What would you like to talk about?");
        helpResponses.add("I'm here to help! You can ask me about various topics or just say hello.");
        responses.put("(help|what can you do|what are your capabilities)", helpResponses);
        
        // Time patterns
        ArrayList<String> timeResponses = new ArrayList<>();
        timeResponses.add("The current time is: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        responses.put("(time|what time is it|current time)", timeResponses);
        
        // Date patterns
        ArrayList<String> dateResponses = new ArrayList<>();
        dateResponses.add("Today's date is: " + java.time.LocalDate.now());
        responses.put("(date|what day is it|today's date)", dateResponses);
        
        // Thank you patterns
        ArrayList<String> thanksResponses = new ArrayList<>();
        thanksResponses.add("You're welcome!");
        thanksResponses.add("Happy to help!");
        thanksResponses.add("Anytime!");
        responses.put("(thank you|thanks|appreciate it)", thanksResponses);
        
        // Goodbye patterns
        ArrayList<String> goodbyeResponses = new ArrayList<>();
        goodbyeResponses.add("Goodbye! Have a great day!");
        goodbyeResponses.add("See you later!");
        goodbyeResponses.add("Bye! Come back soon!");
        responses.put("(bye|goodbye|see you|farewell)", goodbyeResponses);
        
        // Java programming patterns
        ArrayList<String> javaResponses = new ArrayList<>();
        javaResponses.add("Java is a high-level, class-based, object-oriented programming language.");
        javaResponses.add("Java was originally developed by James Gosling at Sun Microsystems and released in 1995.");
        javaResponses.add("Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM).");
        javaResponses.add("Key features of Java include platform independence, object-oriented programming, automatic memory management, and strong type checking.");
        responses.put("(java|jvm|jre|jdk|bytecode)", javaResponses);
        
        // Python programming patterns
        ArrayList<String> pythonResponses = new ArrayList<>();
        pythonResponses.add("Python is an interpreted, high-level, general-purpose programming language.");
        pythonResponses.add("Python was created by Guido van Rossum and first released in 1991.");
        pythonResponses.add("Python's design philosophy emphasizes code readability with its notable use of significant whitespace.");
        pythonResponses.add("Python is dynamically typed and garbage-collected, and supports multiple programming paradigms.");
        responses.put("(python|pylib|pygame|django|flask)", pythonResponses);
        
        // OOP patterns
        ArrayList<String> oopResponses = new ArrayList<>();
        oopResponses.add("Object-Oriented Programming (OOP) is a programming paradigm based on the concept of 'objects'.");
        oopResponses.add("The four main principles of OOP are encapsulation, abstraction, inheritance, and polymorphism.");
        oopResponses.add("OOP languages include Java, C++, Python, C#, and many others.");
        oopResponses.add("In OOP, objects contain data in the form of fields and code in the form of procedures.");
        responses.put("(oop|object oriented|encapsulation|abstraction|inheritance|polymorphism)", oopResponses);
        
        // AI patterns
        ArrayList<String> aiResponses = new ArrayList<>();
        aiResponses.add("Artificial Intelligence (AI) is intelligence demonstrated by machines, unlike natural intelligence displayed by humans.");
        aiResponses.add("AI applications include advanced web search engines, recommendation systems, speech recognition, and more.");
        aiResponses.add("Machine learning is a subset of AI that focuses on building systems that learn from data.");
        aiResponses.add("AI research has been divided into subfields that focus on specific problems or approaches.");
        responses.put("(ai|artificial intelligence|machine learning|neural network|deep learning)", aiResponses);
        
        // Science patterns
        ArrayList<String> scienceResponses = new ArrayList<>();
        scienceResponses.add("Science is a systematic enterprise that builds and organizes knowledge in the form of testable explanations and predictions.");
        scienceResponses.add("The main branches of science are natural sciences, social sciences, and formal sciences.");
        scienceResponses.add("The scientific method involves careful observation, applying rigorous skepticism about what is observed.");
        scienceResponses.add("Science has transformed our understanding of the world and improved quality of life through technology.");
        responses.put("(science|scientific|physics|chemistry|biology|astronomy)", scienceResponses);
        
        // Default responses for unknown inputs
        defaultResponses.add("I'm not sure I understand. Could you rephrase that?");
        defaultResponses.add("That's interesting. Could you tell me more?");
        defaultResponses.add("I'm still learning. Could you try asking differently?");
        defaultResponses.add("I don't have a response for that yet. Maybe ask something else?");
    }
    
    private void createGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setBackground(new Color(60, 63, 65));
        JLabel titleLabel = new JLabel("My AI Chatbot");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBackground(new Color(43, 43, 43));
        chatArea.setForeground(Color.WHITE);
        chatArea.setCaretColor(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(60, 63, 65));
        
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBackground(new Color(50, 50, 50));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        JButton sendButton = new JButton("Send");
        sendButton.setBackground(new Color(0, 120, 215));
        sendButton.setForeground(Color.BLACK);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        
        //Action listeners
        sendButton.addActionListener(e -> processInput());
        inputField.addActionListener(e -> processInput());
        
        //welcome message
        appendToChat("ChatBot", "Hello! I'm your AI assistant. I can explain programming concepts, solve math problems, and more! Type 'help' to see what I can do.");
        
        setVisible(true);
    }
    
    private void appendToChat(String sender, String message) {
        String timestamp = LocalDateTime.now().format(timeFormatter);
        chatArea.append("[" + timestamp + "] " + sender + ": " + message + "\n");
        messageCount++;
    }
    
    private void processInput() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) return;
        
        appendToChat("You", input);
        inputField.setText("");
        
        // Check for special commands first
        if (handleSpecialCommands(input)) {
            return;
        }
        
        // Check if it's a math expression
        if (isMathExpression(input)) {
            String result = evaluateMathExpression(input);
            appendToChat("ChatBot", result);
            return;
        }
        
        // otherwise, generate a response based on patterns
        String response = generateResponse(input.toLowerCase());
        appendToChat("ChatBot", response);
    }
    
    private boolean handleSpecialCommands(String input) {
        switch (input.toLowerCase()) {
            case "help":
                showHelp();
                return true;
            case "stats":
                showStats();
                return true;
            case "clear":
            case "clear history":
                clearHistory();
                return true;
            default:
                return false;
        }
    }
    
    private void showHelp() {
        String helpText = "I support the following commands:\n\n" +
                         "• Ask about programming (Java, Python, OOP)\n" +
                         "• Ask about AI or science topics\n" +
                         "• Math expressions \n" +
                         "• 'help' - Show this help message\n" +
                         "• 'stats' - Show chat statistics\n" +
                         "• 'clear' - Clear chat history";
        appendToChat("ChatBot", helpText);
    }
    
    private void showStats() {
        appendToChat("ChatBot", "Chat statistics: " + messageCount + " messages exchanged so far.");
    }
    
    private void clearHistory() {
        chatArea.setText("");
        appendToChat("ChatBot", "Chat history cleared.");
    }
    
    private boolean isMathExpression(String input) {
        // check if the input looks like a math expression
        Pattern mathPattern = Pattern.compile("^[0-9+\\-*/().^πesincostanlog]+$");
        return mathPattern.matcher(input).find() && 
               input.length() > 1 && // single character is probably not math
               !input.matches(".*[a-df-z].*"); // Contains letters other than e (for exponential)
    }
    
    private String evaluateMathExpression(String expression) {
        try {
            // Replace common math constants and functions for JavaScript
            String processedExpression = expression
                .replace("π", "Math.PI")
                .replace("sin", "Math.sin")
                .replace("cos", "Math.cos")
                .replace("tan", "Math.tan")
                .replace("log", "Math.log")
                .replace("^", "**");
            
            Object result = mathEngine.eval(processedExpression);
            return "The result of " + expression + " is: " + result.toString();
        } catch (ScriptException e) {
            return "I couldn't evaluate that mathematical expression. Please make sure it's valid.";
        }
    }
    
    private String generateResponse(String input) {
        // Check for matches with our response patterns
        for (String pattern : responses.keySet()) {
            if (Pattern.compile(pattern).matcher(input).find()) {
                ArrayList<String> possibleResponses = responses.get(pattern);
                return possibleResponses.get(new Random().nextInt(possibleResponses.size()));
            }
        }
        
        // If no pattern matches, return a default response
        return defaultResponses.get(new Random().nextInt(defaultResponses.size()));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AIChatbot());
    }
}