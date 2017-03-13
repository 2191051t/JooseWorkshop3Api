package gla.cs.joose.workshop.invmgtapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import gla.cs.joose.coursework.invmgt.model.Item;
import gla.cs.joose.coursework.invmgt.model.ItemFactory;
import gla.cs.joose.coursework.invmgt.model.ItemType;
	@Path("invapi")
public class InvAPI {
	
	/** 
	 * This function receives request from rest client to delete an item from the inventory
	 * @param itemid
	 * @param uriinfo
	 * @return Return a Response object containing the status code after delete operation
	 */
	@DELETE
	@Path("/items/{itemid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(@PathParam("itemid") long itemid,@Context UriInfo uriinfo) {		
		boolean deleted = ItemFactory.delete(itemid);
		if(deleted){
			return Response.status(200).build();
		}else{
			return Response.status(204).build();
		}

	}
	
	/**
	 * This function receives request from rest client to retrieve a set of items that matches 
	 * a search pattern from the inventory
	 * @param searchbydesc
	 * @param pattern
	 * @param limit
	 * @param uriinfo
	 * @return return a Response object containing a list of items that matches a search pattern and the status code	 * 		  
	 */
	public Response getItem(String searchbydesc, String pattern,int limit, @Context UriInfo uriinfo) {        
       
		Item[] results = ItemFactory.search(searchbydesc, pattern, limit);
        
        //Task 6
		
		return null;
		
	}
	
	/**
	 * This function receives request from rest client to update an item in the inventory
	 * @param updateitemid
	 * @param newBarcode
	 * @param newItemName
	 * @param newItemType_s
	 * @param newQty
	 * @param newSupplier
	 * @param newDesc
	 * @param uriinfo
	 * @return return a Response object containing  the updated item and the status code
	 */
	@PUT
	@Path("item/{itemid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(Item item,@PathParam("itemid") long itemid,
							   @Context UriInfo uriinfo){	
		boolean updated = false;
		
		boolean deleted = ItemFactory.delete(itemid);
		Item uitem = null;
		
		if(deleted){
			boolean done = ItemFactory.addItem(item);
			if(done){
				updated = true;
			}
		}
		if(updated){
			return Response.status(Status.OK).entity(item).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
					
	}
	
	/**
	 * This function receives request from rest client to add an item to the inventory
	 * @param barcode
	 * @param itemName
	 * @param itemType_s
	 * @param qty
	 * @param supplier
	 * @param desc
	 * @param uriinfo
	 * @return return a Response object containing  the status code of the operation
	 */
	@POST
	@Path("/items") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(Item item,
					        @Context UriInfo uriinfo){		
		boolean done = ItemFactory.addItem(item);
		if(done){
			return Response.status(Status.CREATED).build();
		}else{
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
