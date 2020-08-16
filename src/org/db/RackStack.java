package org.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static com.google.gson.JsonParser.parseReader;

public class RackStack {

  @FXML
  AnchorPane anchor = new AnchorPane();
  @FXML
  ChoiceBox<String> choiceRackSize;

  @FXML
  ListView<RackItem> listViewParts = new ListView<>();
  @FXML
  ListView<RackTotal> listViewTotal = new ListView<>();
  @FXML
  Button buttonQuit, buttonAddRack, buttonLoadParts, buttonLoad, buttonSave;
  @FXML
  ImageView imageViewTrash;

  @FXML
  private void partDragDetected(MouseEvent event) {
    if (event.isDragDetect()) {

      if (listViewParts.getSelectionModel().getSelectedItem() != null) {
        RackItem source = listViewParts.getSelectionModel().getSelectedItem();
        int index = listViewParts.getSelectionModel().getSelectedIndex();
        Dragboard db = listViewParts.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        content.put(dfPart, source);
        content.put(dfIndex, index);
        db.setContent(content);
      }
      event.consume();
    }
  }

  private DataFormat dfPart, dfIndex;
  private static final double ROW_HEIGHT = 18.0;
  private static final double RACK_WIDTH = 100.0;
  private int rackQty = 0;
  private int rackSize;
  private final HashMap<Integer, RackItem> partHashMap = new HashMap<>();
  private final ObservableList<String> rackOptions = FXCollections.observableArrayList("42 U", "48 U");

  public void initialize() {

    defineTrash();

    dfPart = new DataFormat("part");
    dfIndex = new DataFormat("index");

    choiceRackSize.setItems(rackOptions);
    choiceRackSize.setValue("42 U");

    listViewParts.setCellFactory(cellData -> new ListCell<>() {
      @Override
      protected void updateItem(RackItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setAlignment(Pos.CENTER);
          setFont(Font.font(8));
          setText(item.getItemName());
          setStyle("-fx-background-color: " + item.getItemColor());
        }
      }

    });

    listViewTotal.setCellFactory(cellData -> new ListCell<>() {
      @Override
      protected void updateItem(RackTotal item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setAlignment(Pos.CENTER);
          setFont(Font.font(8));
          setText(Integer.toString(item.getItemTotal()));
          setStyle("-fx-background-color: " + item.getItemColor());
        }
      }

    });

  }

  public void ButtonAddRackAction() {


    String rackSizeSelect = choiceRackSize.getSelectionModel().getSelectedItem();
    if (rackSizeSelect != null) {
      if (rackSizeSelect.equals("42 U"))
        rackSize = 42;
      else
        rackSize = 48;

      ListView<RackItem> listViewRack = new ListView<>();
      listViewRack.setPrefWidth(RACK_WIDTH);
      listViewRack.setPrefHeight(rackSize * ROW_HEIGHT + 2.0);
      listViewRack.setLayoutX(350.0 + rackQty * RACK_WIDTH * 1.1);
      listViewRack.setLayoutY(20.0);
      listViewRack.setFixedCellSize(ROW_HEIGHT);
      ++rackQty;
      listViewRack.setId("Rack " + rackQty);

      anchor.getChildren().add(listViewRack);


      ObservableList<RackItem> rackData = FXCollections.observableArrayList();
      String parent = listViewRack.getId();
      for (int i = 0; i < rackSize; i++) {
        RackItem ri = new RackItem(parent, rackSize - i + " Blank ", "#b4ada3", 1);
        partHashMap.put(ri.getId(), ri);
        rackData.add(ri);
      }
      createRack(listViewRack, rackData);
    }

  }

  public void createRack(ListView<RackItem> rack, ObservableList<RackItem> data) {

    rack.setCellFactory(cellData -> {
      ListCell<RackItem> cell = new ListCell<>() {
        @Override
        protected void updateItem(RackItem item, boolean empty) {
          super.updateItem(item, empty);
          if (empty) {
            setText(null);
          } else {
            setAlignment(Pos.CENTER);
            setText(item.getItemName());
            setFont(Font.font(8));
            setStyle("-fx-background-color: " + item.getItemColor());
          }
        }

      };

      cell.setOnDragDetected(event -> {
        if (event.isDragDetect()) {
          if (rack.getSelectionModel().getSelectedItem() != null) {
            RackItem source = rack.getSelectionModel().getSelectedItem();
            Dragboard db = rack.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.put(dfPart, source.getId());
            content.put(dfIndex, rack.getSelectionModel().getSelectedIndex());
            db.setContent(content);
          }
        }
      });

      cell.setOnDragOver(event -> {
        event.acceptTransferModes(TransferMode.COPY);
        event.consume();
      });

      cell.setOnDragDropped(event -> {
        if (event.getDragboard().hasContent(dfPart)) {

          RackItem part = (RackItem) event.getDragboard().getContent(dfPart);
          TextInputDialog countDialog = new TextInputDialog();
          countDialog.setHeaderText("enter part quantity :");
          Optional<String> addCount = countDialog.showAndWait();
          if (addCount.isPresent()) {

            int partIndex = (int) event.getDragboard().getContent(dfIndex);
            RackTotal source = listViewTotal.getItems().get(partIndex);

            int start = cell.getIndex();
            int count = Integer.parseInt(addCount.get());
            int height = part.getItemHeight();
            source.increment(count);

            ObservableList<RackItem> rows = rack.getItems();
            for (int pos = 0; pos < count * height; pos++) {
              RackItem target = rows.get(pos + start);
              if (pos % height == 0)
                target.setItemName(part.getItemName());
              else
                target.setItemName(" ");
              target.setItemColor(part.getItemColor());
              target.setItemIndex(partIndex);
            }
            rack.refresh();
            listViewTotal.refresh();
          }
        }

        event.setDropCompleted(true);
        event.consume();
      });
      return cell;
    });
    rack.setItems(data);
  }

  public void ButtonSaveAction() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    ObservableList<Node> nodes = anchor.getChildren();
    ArrayList<Node> rackNodes = new ArrayList<>();

    for (Node node : nodes) {
      if (node.getClass() == ListView.class)
        if (node.getId().startsWith("Rack")) {
          rackNodes.add(node);
        }
    }

    for (Node rack : rackNodes) {
      JsonArray jsonArray = new JsonArray();
      try {
        FileWriter fw = new FileWriter(rack.getId() + ".json");
        ((ListView<?>) rack).getItems().forEach((element) -> {
          JsonElement json = gson.toJsonTree(element);
          jsonArray.add(json);
        });
        gson.toJson(jsonArray, fw);
        fw.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void ButtonLoadAction() {
    Gson gson = new Gson();
    int rackSize = 42;
    File file;

    while ((file = new File("Rack " + (rackQty + 1) + ".json")).exists()) {

      ObservableList<RackItem> rackData = FXCollections.observableArrayList();
      try {
        FileReader fr = new FileReader(file);
        JsonReader jsonReader = new JsonReader(fr);
        jsonReader.beginArray();

        while (jsonReader.hasNext()) {
          RackItem item = gson.fromJson(parseReader(jsonReader), RackItem.class);
          partHashMap.put(item.getId(), item);
          rackData.add(item);
          if (item.getItemIndex() != -1) {
            RackTotal source = listViewTotal.getItems().get(item.getItemIndex());
            source.increment(1);
          }
        }
        jsonReader.endArray();
        fr.close();
        listViewTotal.refresh();

        ListView<RackItem> listViewRack = new ListView<>();
        listViewRack.setPrefWidth(RACK_WIDTH);
        listViewRack.setPrefHeight(rackSize * ROW_HEIGHT + 2.0);
        listViewRack.setLayoutX(350.0 + rackQty * RACK_WIDTH * 1.1);
        listViewRack.setLayoutY(20.0);
        listViewRack.setFixedCellSize(ROW_HEIGHT);
        ++rackQty;
        listViewRack.setId("Rack " + rackQty);

        anchor.getChildren().add(listViewRack);

        createRack(listViewRack, rackData);

      } catch (IOException e) {
        e.printStackTrace();
      }


    }
  }

  public void ButtonQuitOnAction() {

    Stage stage = (Stage) buttonQuit.getScene().getWindow();
    stage.close();
  }

  public void ButtonLoadPartsOnAction() {
    Gson gson = new Gson();
    int partCount = 0;
    try {

      FileReader fr = new FileReader("parts.json");
      JsonReader jsonReader = new JsonReader(fr);
      jsonReader.beginArray();

      while (jsonReader.hasNext()) {
        RackItem item = gson.fromJson(parseReader(jsonReader), RackItem.class);

        listViewParts.getItems().add(item);
        listViewTotal.getItems().add(new RackTotal(item.getItemColor()));
        ++partCount;
      }
      jsonReader.endArray();
      fr.close();

      listViewParts.setFixedCellSize(ROW_HEIGHT);
      listViewParts.setPrefHeight(ROW_HEIGHT * partCount + 2.0);
      listViewTotal.setFixedCellSize(ROW_HEIGHT);
      listViewTotal.setPrefHeight(ROW_HEIGHT * partCount + 2.0);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void defineTrash() {
    imageViewTrash.setOnDragOver(event -> {
      event.acceptTransferModes(TransferMode.COPY);
      event.consume();
    });
    imageViewTrash.setOnDragEntered(event -> {
      String iconPath = "/img/glass trash.png";
      Image icon = new Image(getClass().getResourceAsStream(iconPath));
      imageViewTrash.setImage(icon);
      event.consume();
    });

    imageViewTrash.setOnDragExited(event -> {
      String iconPath = "/img/trash.png";
      Image icon = new Image(getClass().getResourceAsStream(iconPath));
      imageViewTrash.setImage(icon);
      event.consume();
    });

    imageViewTrash.setOnDragDropped(event -> {
      if (event.getDragboard().hasContent(dfPart)) {
        int sourceId = (int) event.getDragboard().getContent(dfPart);
        int sourceIndex = (int) event.getDragboard().getContent(dfIndex);
        RackItem source = partHashMap.get(sourceId);
        source.setItemName(rackSize - sourceIndex + " Blank");
        source.setItemColor("#b4ada3");

        anchor.getChildren().forEach((node) -> {
          if (node.getClass() == ListView.class) {
            if (node.getId().equals(source.getParentId())) {
              ((ListView<?>) node).refresh();
            }
          }
        });
        RackTotal total = listViewTotal.getItems().get(source.getItemIndex());
        total.decrement(1);
        listViewTotal.refresh();
        source.setItemIndex(-1);
      }
      event.setDropCompleted(true);
      event.consume();

    });

  }
}
