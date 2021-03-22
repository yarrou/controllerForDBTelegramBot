package gui;

import entity.City;
import entity.Response;
import requestHandlers.DeleteRequestHandler;
import requestHandlers.GetRequestHandler;
import requestHandlers.PostRequestHandler;
import requestHandlers.PutRequestHandler;
import settingsProviders.ConnectDBProvider;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    {
        this.provider = new ConnectDBProvider();
    }

    private ConnectDBProvider provider;


    public MainWindow() throws Exception {

        super("управление БД телеграм бота");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JTabbedPane panelVkladok = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);


        JPanel findCityPanel = new JPanel();//вкладка поиска города
        JPanel zaprosPanel = new JPanel();//вкладка ввода запроса
        JLabel iskomiyCityLabel = new JLabel("город");
        JTextField vvodTeksta = new JTextField(14);//поле ввода запроса
        JButton poiskButton = new JButton("поиск");//,poiskImage);//кнопка поиска
        zaprosPanel.add(iskomiyCityLabel);
        zaprosPanel.add(vvodTeksta);
        zaprosPanel.add(poiskButton);
        findCityPanel.add(zaprosPanel, BorderLayout.NORTH);
        JLabel opisanieProductaLabel = new JLabel();//вывод результата поиска
        opisanieProductaLabel.setPreferredSize(new Dimension(355, 70));
        opisanieProductaLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel vivodPanel = new JPanel();
        vivodPanel.setLayout(new FlowLayout());
        //vivodPanel.add(iconProductLabel);
        vivodPanel.add(opisanieProductaLabel);
        vivodPanel.setBorder(new TitledBorder("вывод результата"));
        vivodPanel.setPreferredSize(new Dimension(450, 200));
        findCityPanel.add(vivodPanel, BorderLayout.CENTER);

        poiskButton.addActionListener(new ActionListener() {//добавляем метод поиска к кнопке поиска
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GetRequestHandler getRequestHandler = new GetRequestHandler(provider);
                StringBuilder message = new StringBuilder();
                try {
                    String cityName = vvodTeksta.getText();
                    Response response = getRequestHandler.sendRequest(cityName);
                    message.append(response.getStatus()).append(" ").append(response.getMessage());

                } catch (IOException e) {
                    message.append(e.getClass().getName()).append(" ").append(e.getMessage());
                }
                opisanieProductaLabel.setText(message.toString());
            }
        });


        //Post вкладка
        JPanel postPanel = new JPanel();
        JLabel cityNameLabel = new JLabel("город");
        JLabel responseCodeLabel = new JLabel();
        JButton cityCreateButton = new JButton("добавить");//кнопка добавления данных нового города
        JTextField newCityNameTextField = new JTextField(14);//ввод названия нового города
        JLabel newCityResponseMessageLabel = new JLabel();//вывод сообщения ответа
        JPanel newCityNamePanel = new JPanel();//панель для названия нового города
        newCityNamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newCityNamePanel.setPreferredSize(new Dimension(450, 40));
        newCityNamePanel.add(cityNameLabel);
        newCityNamePanel.add(newCityNameTextField);
        newCityNamePanel.add(cityCreateButton);
        //newCityNamePanel.add(responseCodeLabel);
        JLabel opisanieCityLabel = new JLabel("описание");
        JTextArea newCityTextArea = new JTextArea(4, 25);
        JPanel newCityDescriptionPanel = new JPanel();
        newCityDescriptionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newCityDescriptionPanel.setPreferredSize(new Dimension(450, 40));
        newCityDescriptionPanel.add(opisanieCityLabel);
        newCityDescriptionPanel.add(newCityTextArea);

        cityCreateButton.addActionListener(new ActionListener() {//метод добавления данных нового города

            public void actionPerformed(ActionEvent event) {
                City city = new City(newCityNameTextField.getText(), newCityTextArea.getText());
                PostRequestHandler postRequestHandler = new PostRequestHandler(provider);
                String code = null;
                String message = null;
                try {
                    Response response = postRequestHandler.sendRequest(city);
                    code = response.getStatus() + "";
                    message = response.getMessage();
                } catch (IOException e) {
                    code = e.getClass().getName();
                    message = e.getMessage();
                    e.printStackTrace();

                }
                responseCodeLabel.setText(code);
                newCityResponseMessageLabel.setText(message);

            }

        });
        JPanel newCitypanelFinal = new JPanel();
        newCitypanelFinal.add(responseCodeLabel);
        newCitypanelFinal.add(newCityResponseMessageLabel);

        Box newCityBox = Box.createVerticalBox();//панель POST
        newCityBox.add(newCityNamePanel);
        newCityBox.add(newCityDescriptionPanel);
        newCityBox.add(newCitypanelFinal);

        //панель PUT
        JPanel putPanel = new JPanel();
        JLabel pcityNameLabel = new JLabel("город");
        //JLabel presponseCodeLabel=new JLabel();
        JTextField psozProdNameTextfield = new JTextField(14);//ввод названия города который хотим изменить
        JButton cityChangeButton = new JButton("изменить");//кнопка изменения данных  города
        JPanel psozProdNamePanel = new JPanel();//панель для названия изменяемого города
        psozProdNamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        psozProdNamePanel.setPreferredSize(new Dimension(450, 40));
        psozProdNamePanel.add(pcityNameLabel);
        psozProdNamePanel.add(psozProdNameTextfield);
        psozProdNamePanel.add(cityChangeButton);
        JLabel popisanieCityLabel = new JLabel("описание");
        JTextArea psozCityTextArea = new JTextArea(4, 25);
        JPanel psozProdCaloriiPanel = new JPanel();
        psozProdCaloriiPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        psozProdCaloriiPanel.setPreferredSize(new Dimension(450, 40));
        psozProdCaloriiPanel.add(popisanieCityLabel);
        psozProdCaloriiPanel.add(psozCityTextArea);
        JLabel changeResponseLabel = new JLabel();

        cityChangeButton.addActionListener(new ActionListener() {//метод изменения данных  города

            public void actionPerformed(ActionEvent event) {
                City city = new City(psozProdNameTextfield.getText(), psozCityTextArea.getText());
                PutRequestHandler putRequestHandler = new PutRequestHandler(provider);
                String code = null;
                String message = null;
                try {
                    Response response = putRequestHandler.sendRequest(city);
                    code = response.getStatus() + "";
                    message = response.getMessage();
                } catch (IOException e) {
                    message = e.getClass().getName();
                    code = e.getMessage();
                    e.printStackTrace();
                }
                changeResponseLabel.setText(code + " " + message);

            }

        });
        JPanel ppanelFinal = new JPanel();
        ppanelFinal.add(changeResponseLabel);

        Box cityChangeBox = Box.createVerticalBox();//панель POST
        cityChangeBox.add(psozProdNamePanel);
        cityChangeBox.add(psozProdCaloriiPanel);
        cityChangeBox.add(ppanelFinal);


        //Delete вкладка
        JPanel deletePanel = new JPanel();
        JLabel cityDeleteNameLabel = new JLabel("город");
        JLabel responseDeleteCodeLabel = new JLabel();
        JButton cityDeleteButton = new JButton("удалить");//кнопка удаления данных  города
        JTextField cityDeleteNameTextField = new JTextField(14);//ввод названия удаляемого города
        JPanel cityDeleteNamePanel = new JPanel();//панель для названия удаляемого продукта
        cityDeleteNamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cityDeleteNamePanel.setPreferredSize(new Dimension(450, 40));
        cityDeleteNamePanel.add(cityDeleteNameLabel);
        cityDeleteNamePanel.add(cityDeleteNameTextField);
        cityDeleteNamePanel.add(cityDeleteButton);
        JLabel resultDeleteCityLabel = new JLabel("результат");
        JLabel cityDeleteOutputLabel = new JLabel();
        JPanel cityDeletePanel = new JPanel();
        cityDeletePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        cityDeletePanel.setPreferredSize(new Dimension(450, 40));
        cityDeletePanel.add(resultDeleteCityLabel);
        cityDeletePanel.add(responseDeleteCodeLabel);
        cityDeletePanel.add(cityDeleteOutputLabel);

        cityDeleteButton.addActionListener(new ActionListener() {//метод удаления данных  города

            public void actionPerformed(ActionEvent event) {
                DeleteRequestHandler deleteRequestHandler = new DeleteRequestHandler(provider);
                StringBuilder message = new StringBuilder();
                try {
                    String cityName = cityDeleteNameTextField.getText();
                    Response response = deleteRequestHandler.sendRequest(cityName);
                    message.append(response.getStatus()).append(" ").append(response.getMessage());

                } catch (IOException e) {
                    message.append(e.getClass().getName()).append(" ").append(e.getMessage());
                    e.printStackTrace();
                }
                cityDeleteOutputLabel.setText(message.toString());
                resultDeleteCityLabel.setText("");
            }
        });


        Box cityDeletBox = Box.createVerticalBox();//панель DELETE
        cityDeletBox.add(cityDeleteNamePanel);
        cityDeletBox.add(cityDeletePanel);


        //добавляем вкладки
        panelVkladok.add("GET", findCityPanel);
        panelVkladok.add("POST", newCityBox);
        panelVkladok.add("PUT", cityChangeBox);
        panelVkladok.add("DELETE", cityDeletBox);


        getContentPane().add(panelVkladok);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//вычисляем размер экрана
        int sizeWidth = 450;//ширина окна программы
        int sizeHeight = 300;//высота окна программы
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        setBounds(locationX, locationY, sizeWidth, sizeHeight);//присваиваем окну программы размер и положение относительно центра экрана
    }
}