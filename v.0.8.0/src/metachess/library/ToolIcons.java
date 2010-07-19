package metachess.library;

public enum ToolIcons {

    LEFT_ARROW("../resources/images/icons/back.png"),
	RIGHT_ARROW("../resources/images/icons/next.png"),
	GAME("../resources/images/icons/Wking.png"),
	NEW("../resources/images/icons/add.png"),
	SAVE("../resources/images/icons/accept.png"),
	LOAD("../resources/images/icons/down.png"),
	EXIT("../resources/images/icons/remove.png"),
	BUILDER("../resources/images/icons/process.png");

   private String link;

   private ToolIcons(String link) {
       this.link = link;
   }

   public String getPath() {
       return link;
   }


}
