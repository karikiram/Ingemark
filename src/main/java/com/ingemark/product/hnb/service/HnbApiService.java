package com.ingemark.product.hnb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

/**
 * HnbApiService provides methods to fetch the current EUR to USD exchange rate
 * from the Croatian National Bank (HNB) API.
 * It uses RestTemplate to make HTTP requests and parse the response.
 */
@Service
public class HnbApiService {
	/** URL for the HNB API to get the EUR to USD exchange rate. */
	private static final String HNB_API_URL = "https://api.hnb.hr/tecajn-eur/v3?valuta=USD";

	/**
	 * Fetches the current EUR to USD exchange rate from the HNB API.
	 * The rate is returned as a BigDecimal after parsing the response.
	 *
	 * @return the current EUR to USD exchange rate
	 */
	public BigDecimal getEurToUsdRate() {
		RestTemplate restTemplate = new RestTemplate();
		String exchangeRate = restTemplate.getForObject(HNB_API_URL, String.class);
		if (Strings.isNotEmpty(exchangeRate)) {
			String averageExchangeRate = getAverageExchangeRate(exchangeRate);
			return new BigDecimal(averageExchangeRate.replace(",", "."));
		}
		throw new RuntimeException("Failed to fetch exchange rate from HNB API");
	}

	/**
	 * Parses the JSON response from the HNB API to extract the "srednji_tecaj" value.
	 * This value represents the average exchange rate for EUR to USD.
	 *
	 * @param exchangeRate the JSON response string from the HNB API
	 * @return the "average exchange rate value as a String
	 */
	public String getAverageExchangeRate(String exchangeRate) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(exchangeRate);
			if (root.isArray() && !root.isEmpty()) {
				return root.get(0).get("srednji_tecaj").asText();
			}
			throw new RuntimeException("No data found in HNB api JSON");
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse HNB api data JSON", e);
		}
	}
}