package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.lambda.response.HandlerResponse;
import com.iwe.avengers.dao.AvengerDAO;

public class CreateAvengersHandler implements RequestHandler<Avenger, HandlerResponse> {
	
	private AvengerDAO dao = new AvengerDAO();

	@Override
	public HandlerResponse handleRequest(final Avenger newAvenger, final Context context) {

		context.getLogger().log("[#] - Initiate a new Avenger registration");

		final Avenger createdAvenger = dao.create(newAvenger);
		
		final HandlerResponse response = HandlerResponse.builder()
										.setStatusCode(201)
										.setObjectBody(createdAvenger)
										.build();
		
		context.getLogger().log("[#] - Avenger registered successfully");
		return response;
	}
}
