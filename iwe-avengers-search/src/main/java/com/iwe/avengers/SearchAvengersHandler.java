package com.iwe.avengers;

import java.util.NoSuchElementException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.lambda.exception.AvengerNotFoundException;
import com.iwe.avenger.lambda.response.HandlerResponse;
import com.iwe.avengers.dao.AvengerDAO;

public class SearchAvengersHandler implements RequestHandler<Avenger, HandlerResponse> {

	private AvengerDAO dao = AvengerDAO.getInstance();
	
	@Override
	public HandlerResponse handleRequest(final Avenger avenger, final Context context) {
		
		context.getLogger().log("[#] - Search Avenger with id"+avenger.getId());
		try {
			final Avenger retrievedAvenger = dao.find(avenger.getId());
			context.getLogger().log("[#] - Avenger found =) ");
			
			return HandlerResponse.builder()
						.setStatusCode(200)
						.setObjectBody(retrievedAvenger)
						.build();

		} catch (NoSuchElementException e) {
			throw new AvengerNotFoundException("[NotFound] - Avenger id:"+avenger.getId()+" not found");
		}
	}
}
