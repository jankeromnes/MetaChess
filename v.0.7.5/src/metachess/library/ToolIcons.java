package metachess.library;

public enum ToolIcons {

    LEFT_ARROW("../resources/images/icons/left_arrow.gif"),
	GOTO_ARROW("../resources/images/icons/goto_arrow.png"),
	RIGHT_ARROW("../resources/images/icons/right_arrow.gif");

   private String link;

   private ToolIcons(String link) {
       this.link = link;
   }

   public String getPath() {
       return link;
   }


}
