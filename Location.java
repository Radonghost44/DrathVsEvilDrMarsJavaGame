public class Location {

  // Global
  public static int locationId;  //array stores all of the location number ids, 0-15 
  public static String locationName=String [];
  public static String locationDiscription;
  public static String locationItem; // will call on anther class, Item in the future.
  // now for my next constutuctor
  Location KennelTown=new Location (locationId,locationName,locationDiscription,locationItem);{
  // system out print to make sure the class works. later will be replaced with acutal items and id used for navgation
   System.out.print(locationId);
   System.out.print(locationName);
   System.out.print(locationDiscription);
   System.out.print(locationItem);

                                         }
  // now for the setter methods
     public void setId (int id){
      int id=locationId;
         }
      public void setName (String name){
      String name=locationName;
             }
      public void setDiscription (String discription){
      String name=locationName;
             }
       public void setItem (boolan item){
      boolan item=locationItem;
             }
  // now for the getter methods
       public int getId () {
         System.out.print("Location's id number :"+locationId);
         return locationId;
             }
       public String getName () {
        System.out.print("Location's name is : "+locationName);
          return locationName;
             }
      public Sting getDiscription () {
        System.out.print("Location's discription :"+locationDiscription);
          return locationDiscription;
             }
       
      public String getItem () {
        System.out.print("Location's item is "+ locationItem);
         return locationItem;
             }
         
  public static void main(String[] args) {
  // now for an instance of location class 0, kennel town
   Location kennelTown= new Location;
    Location.setId (0);
    Loaction.getId();
    Location.setName ("Kennel Town");
    Loaction.getName();
    Location.setDiscription ("A peaceful suburb of Cat Nip City.");
    Loaction.getDiscription();
    Location.setItem ("There are no items in Kennel Town");
    Loaction.getItem();

                                        }
                                          }