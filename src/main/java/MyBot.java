import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi tbApi = new TelegramBotsApi();
        try {
            tbApi.registerBot(new MyBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message !=null && message.hasText())
            System.out.println(message.getText());
        switch (message.getText()){
            case "hi":
                sendMessage(message, "Hi there :)");
                break;
            case "/list":
                sendMessage(message, "Введите название города, чтобы узнать погоду");
                break;
            default:
                sendMessage(message, WeatherForecast.getWeather(message.getText()));
                break;
        }
    }

    @Override
    public String getBotUsername() {
        return "danilmv_bot";
    }

    @Override
    public String getBotToken() {
        return "1437529619:AAGoxHMF2nyFFAevtGucbBRdsNn32OGN8T0";
    }

    public void sendMessage(Message messge, String str){
        SendMessage sendMsg = new SendMessage();
        sendMsg.enableMarkdown(true);
        sendMsg.setChatId(messge.getChatId());
        sendMsg.setReplyToMessageId(messge.getMessageId());
        sendMsg.setText(str);
        try {
            setButtons(sendMsg);
            sendMessage(sendMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(keyboard);
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyRows = new ArrayList<>();
        KeyboardRow keyRow1 = new KeyboardRow();

        keyRow1.add(new KeyboardButton("hi"));
        keyRow1.add(new KeyboardButton("/list"));

        keyRows.add(keyRow1);

        keyboard.setKeyboard(keyRows);
    }
}
