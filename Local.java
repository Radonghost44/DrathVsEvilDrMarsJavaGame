public class Locale {

   //
   // -- PUBLIC -- 
   //

   public Locale(int theId) {
      id = theId;
   }
  
   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }
   public void setName(String value) {
      name = value;
   }   
   
   public String getDesc() {
      return desc;
   }
   public void setDesc(String value) {
      desc = value;
   }

   //
   // -- PRIVATES -- 
   //
   private int    id;
   private String name;
   private String desc;

}