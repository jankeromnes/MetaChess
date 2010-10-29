package metachess.library;

public enum ToolIcons {

    LEFT_ARROW("back.png"),
	RIGHT_ARROW("next.png"),
	GAME("Wking.png"),
	NEW("add.png"),
	SAVE("accept.png"),
	LOAD("down.png"),
	EXIT("remove.png"),
	BUILDER("process.png");

   private String link;

   private ToolIcons(String link) {
       this.link = link;
   }

   private String getFolderPath() {
       return "/resources/images/icons/";
   }

   public String getPath() {
       return getFolderPath()+link;
   }


}
