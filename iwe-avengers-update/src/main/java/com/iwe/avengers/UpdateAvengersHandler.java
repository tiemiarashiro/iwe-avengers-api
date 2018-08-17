package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.lambda.exception.AvengerNotFoundException;
import com.iwe.avenger.lambda.response.HandlerResponse;
import com.iwe.avengers.dao.AvengerDAO;

public class UpdateAvengersHandler implements RequestHandler<Avenger, HandlerResponse> {

	private AvengerDAO dao = new AvengerDAO();
	
	@Override
	public HandlerResponse handleRequest(final Avenger avenger, final Context context) {
		final String id = avenger.getId();
		context.getLogger().log("[#] - Update Avenger with id"+id);
		
		final Avenger retrievedAvenger = dao.find(id);
		
		if(retrievedAvenger == null) {
			throw new AvengerNotFoundException("[NotFound] - Avenger id:"+id+" not found");
		}
		
		Avenger updatedAvenger = dao.update(avenger);
		
		final HandlerResponse response = HandlerResponse.builder()
										.setStatusCode(200)
										.setObjectBody(updatedAvenger)
										.build();
		
		context.getLogger().log("[#] - Avenger updated =) ");
		return response;
	}
}
