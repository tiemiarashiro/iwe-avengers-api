package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.lambda.exception.AvengerNotFoundException;
import com.iwe.avenger.lambda.response.HandlerResponse;
import com.iwe.avengers.dao.AvengerDAO;

public class SearchAvengersHandler implements RequestHandler<Avenger, HandlerResponse> {

	private AvengerDAO dao = new AvengerDAO();
	
	@Override
	public HandlerResponse handleRequest(final Avenger avenger, final Context context) {
		final String id = avenger.getId();
		context.getLogger().log("[#] - Delete Avenger with id"+id);
		
		final Avenger retrievedAvenger = dao.find(id);
		
		if(retrievedAvenger == null) {
			throw new AvengerNotFoundException("[NotFound] - Avenger id:"+id+" not found");
		}
		
		final HandlerResponse response = HandlerResponse.builder()
										.setStatusCode(200)
										.setObjectBody(retrievedAvenger)
										.build();
		
		context.getLogger().log("[#] - Avenger found =) ");
		return response;
	}
}
