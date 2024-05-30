package com.javarush.telegram;

import com.javarush.telegram.ChatGPTService;
import com.javarush.telegram.DialogMode;
import com.javarush.telegram.MultiSessionTelegramBot;
import com.javarush.telegram.UserInfo;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Arrays;

public class TinderBoltApp extends MultiSessionTelegramBot
{
    public static final String TELEGRAM_BOT_NAME = "MyFirstTinderAIBot"; //TODO: добавь имя бота в кавычках
    public static final String TELEGRAM_BOT_TOKEN = "7312937234:AAGHvM8I-nNDTXiy0Sayvd6LqbAy6y4EH1g"; //TODO: добавь токен бота в кавычках
    public static final String OPEN_AI_TOKEN = ""; //TODO: добавь токен ChatGPT в кавычках

    private ChatGPTService chatGPT = new ChatGPTService(OPEN_AI_TOKEN);
    private DialogMode currentMode = null;//текущий режим диалога
    private ArrayList<String> list = new ArrayList<>();//список сообщений по переписке
    private UserInfo me;
    private UserInfo she;
    private int questionCount;
    public TinderBoltApp() {
        super(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN);
    }


    @Override
    public void onUpdateEventReceived(Update update)//TODO: основной функционал бота будем писать здесь
    {
        // 1-ый вариант
//       String message = getMessageText();
//       if(message.equals("/start"))
//       {
//           currentMode = DialogMode.MAIN;
//           sendPhotoMessage("main");//фото обложка
//           String text = loadMessage("main");//Наше приветственное сообщение(messages)
//           sendTextMessage(text);
//
//           showMainMenu("главное меню бота", "/start",
//                   "генерация Tinder-профиля \uD83D\uDE0E", "/profile",
//                   "сообщение для знакомства \uD83E\uDD70", "/opener",
//                   "переписка от вашего имени \uD83D\uDE08", "/message",
//                   "переписка со звездами \uD83D\uDD25", "/date",
//                   "задать вопрос чату GPT \uD83E\uDDE0", "/gpt");
//           return;
//       }
//
//       //command GPT
//       if(message.equals("/gpt"))
//       {
//           currentMode = DialogMode.GPT;
//           sendPhotoMessage("gpt");
//           sendTextMessage(loadMessage("gpt"));
//           return;
//       }
//
//       if(currentMode == DialogMode.GPT && !isMessageCommand())
//       {
//           String prompt = loadPrompt("gpt");
//           Message msg = sendTextMessage("Подождите пару секунд - ChatGPT думает...");
//           String answer = chatGPT.sendMessage(prompt, message);
//           updateTextMessage(msg, answer);
//           return;
//       }
//
//       //command DATE
//       if(message.equals("/date"))
//       {
//           currentMode = DialogMode.DATE;
//           sendPhotoMessage("date");
//           sendTextButtonsMessage
//                   (loadMessage("date"),
//                   "Ариана Гранде","date_grande",
//                   "Марго Робби","date_robbie",
//                   "Зендея","date_zendaya",
//                   "Райан Гослинг","date_gosling",
//                   "Том Харди","date_hardy"
//                   );
//           return;
//       }
//       if(currentMode == DialogMode.DATE && !isMessageCommand())
//       {
//           String query = getCallbackQueryButtonKey();
//           if(query.startsWith("date_"))
//           {
//               sendPhotoMessage(query);
//               sendTextMessage("Отличный выбор!\nТвоя задача пригласить девушку на свидание \uD83D\uDC97 за 5 сообщений.");
//               chatGPT.setPrompt(loadPrompt(query));
//               return;
//           }
//           Message msg = sendTextMessage("Подождите, набирает текст...");
//           String answer = chatGPT.addMessage(message);
//           updateTextMessage(msg, answer);
//           return;
//       }
//
//        //command Message
//        if(message.equals("/message"))
//        {
//            currentMode = DialogMode.MESSAGE;
//            sendPhotoMessage("message");
//            sendTextButtonsMessage("Пришлите в чат вашу переписку",
//                    "Следующее сообщение", "message_next",
//                    "Пригласить на свидание", "message_date");
//            return;
//        }
//        if(currentMode == DialogMode.MESSAGE && !isMessageCommand())
//        {
//            String query = getCallbackQueryButtonKey();
//            if(query.startsWith("message_"))
//            {
//                String userChatHistory = String.join("\n\n", list);
//                Message msg = sendTextMessage("Подождите пару секунд - ChatGPT \uD83E\uDDE0 думает...");
//                String answer = chatGPT.sendMessage(loadPrompt(query), userChatHistory);
//                updateTextMessage(msg, answer);
//            }
//
//            list.add(message);
//
//            return;
//        }
//
//
//        //command PROFILE
//        if(message.equals("/profile"))
//        {
//            currentMode = DialogMode.PROFILE;
//            sendPhotoMessage("profile");
//
//            me = new UserInfo();
//            questionCount = 1;
//            sendTextMessage("Сколько вам лет?");
//
//            return;
//        }
//        if(currentMode == DialogMode.PROFILE && !isMessageCommand())
//        {
//            switch (questionCount)
//            {
//                case 1:
//                    me.age = message;
//                    questionCount = 2;
//                    sendTextMessage("Кем вы работаете?");
//                    return;
//                case 2:
//                    me.occupation = message;
//                    questionCount = 3;
//                    sendTextMessage("У вас есть хобби?");
//                    return;
//                case 3:
//                    me.hobby = message;
//                    questionCount = 4;
//                    sendTextMessage("Что вам НЕ нравится в людях?");
//                    return;
//                case 4:
//                    me.annoys = message;
//                    questionCount = 5;
//                    sendTextMessage("Какая ваша цель знакомства?");
//                    return;
//                case 5:
//                    me.goals = message;
//                    String aboutMyself = me.toString();
//                    Message msg = sendTextMessage("Подождите пару секунд - ChatGPT \uD83E\uDDE0 думает...");
//                    String answer = chatGPT.sendMessage(loadPrompt("profile"), aboutMyself);
//                    updateTextMessage(msg, answer);
//                    return;
//            }
//            return;
//        }
//
//        //command OPENER
//        if(message.equals("/opener"))
//        {
//            currentMode = DialogMode.OPENER;
//            sendPhotoMessage("opener");
//            she = new UserInfo();
//            questionCount = 1;
//            sendTextMessage("Имя девушки?");
//            return;
//        }
//
//        if(currentMode == DialogMode.OPENER && !isMessageCommand())
//        {
//            switch (questionCount)
//            {
//                case 1:
//                    she.name = message;
//                    questionCount = 2;
//                    sendTextMessage("Сколько ей лет?");
//                    return;
//                case 2:
//                    she.age = message;
//                    questionCount = 3;
//                    sendTextMessage("Есть ли у нее хобби и какие?");
//                    return;
//                case 3:
//                    she.hobby = message;
//                    questionCount = 4;
//                    sendTextMessage("Кем она работает");
//                    return;
//                case 4:
//                    she.occupation = message;
//                    questionCount = 5;
//                    sendTextMessage("Цель знакомства?");
//                    return;
//                case 5:
//                    she.goals = message;
//                    String aboutFriend = message;
//                    Message msg = sendTextMessage("Подождите пару секунд - ChatGPT \uD83E\uDDE0 думает...");
//                    String answer = chatGPT.sendMessage(loadPrompt("opener"), aboutFriend);
//                    updateTextMessage(msg, answer);
//                    return;
//            }
//
//            return;
//
//        }
//       sendTextButtonsMessage("Выберите режим работы:",
//               "Старт", "start",
//               "Стоп", "stop");



        //2-ой вариант
        String message = getMessageText();

        if (isMessageCommand())
        {
            handleCommand(message);
        }
        else
        {
            handleMessage(message);
        }


    }

    public static void main(String[] args) throws TelegramApiException
    {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TinderBoltApp());
    }




    private void handleCommand(String message)
    {
        switch (message)
        {
            case "/start":
                handleStartCommand();
                break;
            case "/gpt":
                handleGptCommand();
                break;
            case "/date":
                handleDateCommand();
                break;
            case "/message":
                handleMessageCommand();
                break;
            case "/profile":
                handleProfileCommand();
                break;
            case "/opener":
                handleOpenerCommand();
                break;
            default:
                handleUnknownCommand(message);
                break;
        }
    }



    private void handleMessage(String message)
    {
        switch (currentMode)
        {
            case GPT:
                handleGptMode(message);
                break;
            case MAIN:
                sendTextMessage("*Hello*");
                break;
            case DATE:
                handleDateMode(message);
                break;
            case MESSAGE:
                handleMessageMode(message);
                break;
            case PROFILE:
                handleProfileMode(message);
                break;
            case OPENER:
                handleOpenerMode(message);
                break;
            default:
                sendTextButtonsMessage("Выберите режим работы:",
                        "Старт", "start",
                        "Стоп", "stop");
                break;
        }
    }

    /**
     * Start command
     */
    private void handleStartCommand()
    {
        currentMode = DialogMode.MAIN;
        sendPhotoMessage("main");
        String text = loadMessage("main");
        sendTextMessage(text);

        showMainMenu("главное меню бота", "/start",
                "генерация Tinder-профиля \uD83D\uDE0E", "/profile",
                "сообщение для знакомства \uD83E\uDD70", "/opener",
                "переписка от вашего имени \uD83D\uDE08", "/message",
                "переписка со звездами \uD83D\uDD25", "/date",
                "задать вопрос чату GPT \uD83E\uDDE0", "/gpt");
    }

    /**
     * ChatGPT command
     */
    private void handleGptCommand()
    {
        currentMode = DialogMode.GPT;
        sendPhotoMessage("gpt");
        sendTextMessage(loadMessage("gpt"));
    }

    private void handleGptMode(String message)
    {
        String prompt = loadPrompt("gpt");
        Message msg = sendTextMessage("Подождите пару секунд - ChatGPT думает...");
        String answer = chatGPT.sendMessage(prompt, message);
        updateTextMessage(msg, answer);
    }

    /**
     * Date command
     */
    private void handleDateCommand()
    {
        currentMode = DialogMode.DATE;
        sendPhotoMessage("date");
        sendTextButtonsMessage
                (loadMessage("date"),
                        "Ариана Гранде","date_grande",
                        "Марго Робби","date_robbie",
                        "Зендея","date_zendaya",
                        "Райан Гослинг","date_gosling",
                        "Том Харди","date_hardy"
                );
    }
    private void handleDateMode(String message)
    {
        String query = getCallbackQueryButtonKey();
        if(query.startsWith("date_"))
        {
            sendPhotoMessage(query);
            sendTextMessage("Отличный выбор!\nТвоя задача пригласить девушку/парня на свидание \uD83D\uDC97 за 5 сообщений.");
            chatGPT.setPrompt(loadPrompt(query));
            return;
        }
        Message msg = sendTextMessage("Подождите, набирает текст...");
        String answer = chatGPT.addMessage(message);
        updateTextMessage(msg, answer);
    }

    /**
     * Message command
     */

    private void handleMessageCommand()
    {
        currentMode = DialogMode.MESSAGE;
        sendPhotoMessage("message");
        sendTextButtonsMessage("Пришлите в чат вашу переписку",
                    "Следующее сообщение", "message_next",
                    "Пригласить на свидание", "message_date");
    }
    private void handleMessageMode(String message)
    {
        String query = getCallbackQueryButtonKey();
        if(query.startsWith("message_"))
        {
            String userChatHistory = String.join("\n\n", list);
            Message msg = sendTextMessage("Подождите пару секунд - ChatGPT думает...");
            String answer = chatGPT.sendMessage(loadPrompt(query), userChatHistory);
            updateTextMessage(msg, answer);
        }

        list.add(message);
    }

    /**
     * Profile command
     */
    private void handleProfileCommand()
    {
        currentMode = DialogMode.PROFILE;
        sendPhotoMessage("profile");

        me = new UserInfo();
        questionCount = 1;
        sendTextMessage("Сколько вам лет?");
    }
    private void handleProfileMode(String message)
    {
        switch (questionCount)
        {
            case 1:
                me.age = message;
                questionCount = 2;
                sendTextMessage("Кем вы работаете?");
                return;
            case 2:
                me.occupation = message;
                questionCount = 3;
                sendTextMessage("У вас есть хобби и какие?");
                return;
            case 3:
                me.hobby = message;
                questionCount = 4;
                sendTextMessage("Что вам НЕ нравится в людях?");
                return;
            case 4:
                me.annoys = message;
                questionCount = 5;
                sendTextMessage("Какая ваша цель знакомства?");
                return;
            case 5:
                me.goals = message;
                String aboutMyself = me.toString();
                Message msg = sendTextMessage("Подождите пару секунд - ChatGPT \uD83E\uDDE0 думает...");
                String answer = chatGPT.sendMessage(loadPrompt("profile"), aboutMyself);
                updateTextMessage(msg, answer);
                return;
        }
    }

    /**
     * Opener command
     */
    private void handleOpenerCommand()
    {
        currentMode = DialogMode.OPENER;
        sendPhotoMessage("opener");
        she = new UserInfo();
        questionCount = 1;
        sendTextMessage("Имя девушки?");
    }
    private void handleOpenerMode(String message)
    {
        switch (questionCount)
        {
            case 1:
                she.name = message;
                questionCount = 2;
                sendTextMessage("Сколько ей лет?");
                return;
            case 2:
                she.age = message;
                questionCount = 3;
                sendTextMessage("Есть ли у нее хобби и какие?");
                return;
            case 3:
                she.hobby = message;
                questionCount = 4;
                sendTextMessage("Кем она работает");
                return;
            case 4:
                she.occupation = message;
                questionCount = 5;
                sendTextMessage("Цель знакомства?");
                return;
            case 5:
                she.goals = message;
                String aboutFriend = message;
                Message msg = sendTextMessage("Подождите пару секунд - ChatGPT \uD83E\uDDE0 думает...");
                String answer = chatGPT.sendMessage(loadPrompt("opener"), aboutFriend);
                updateTextMessage(msg, answer);
                return;
        }
    }

    private void handleUnknownCommand(String command)
    {
        sendTextMessage("Неизвестная команда: " + command);
    }
}
