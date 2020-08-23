package com.usoft.delegate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.usoft.dtos.jira.BoardList;
import com.usoft.dtos.jira.IssueType;
import com.usoft.dtos.jira.Priority;
import com.usoft.dtos.jira.Sprint;
import com.usoft.dtos.jira.SprintList;
import com.usoft.dtos.jira.SprintReport;
import com.usoft.dtos.jira.Status;
import com.usoft.dtos.jira.UserJira;
import com.usoft.dtos.jira.VelocityChartElement;

@Component
public class JiraApiRestClient implements JiraApiRestClientInt {

	private final String BASE_ROUTE = "https://pi2tools.icesi.edu.co/jira/rest/";
	private final String USERNAME = "leonardo.franco";
	private final String PASSWORD = "Ab2304lf33#1";

	private RestTemplate restTemplate;

	public JiraApiRestClient() {
		restTemplate = new RestTemplate();
	}

	public HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			private static final long serialVersionUID = 1L;

			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

	@Override
	public SprintReport getSprintReport(int boardId, int sprintId) {

		String url = BASE_ROUTE + "greenhopper/latest/rapid/charts/sprintreport?rapidViewId=" + boardId + "&sprintId="
				+ sprintId;
		ResponseEntity<SprintReport> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<SprintReport>() {
					});
			SprintReport sprintReport = response.getBody();
			return sprintReport;

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<VelocityChartElement> getVelocityChart(int boardId) {
		String url = BASE_ROUTE + "greenhopper/1.0/rapid/charts/velocity?rapidViewId=" + boardId;
		ResponseEntity<JsonNode> response = null;
		try {

			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<JsonNode>() {
					});
			JsonNode jsonResponse = response.getBody();

			ArrayNode sprints = (ArrayNode) jsonResponse.get("sprints");

			Iterator<JsonNode> elements = sprints.elements();

			List<VelocityChartElement> velocityChart = new ArrayList<>();

			while (elements.hasNext()) {
				JsonNode sprintField = elements.next();

				VelocityChartElement velocityElement = new VelocityChartElement();

				Sprint newSprint = new Sprint();
				newSprint.setId(sprintField.get("id").asInt());
				newSprint.setName(sprintField.get("name").asText());
				newSprint.setState(sprintField.get("state").asText());
				newSprint.setGoal(sprintField.get("goal").asText());

				velocityElement.setSprint(newSprint);
				velocityElement.setId(newSprint.getId());
				velocityChart.add(velocityElement);

			}

			ObjectNode velocityStatEntries = (ObjectNode) jsonResponse.get("velocityStatEntries");
			for (VelocityChartElement vce : velocityChart) {
				String sprintId = vce.getSprint().getId() + "";

				JsonNode statEntry = velocityStatEntries.get(sprintId);
				vce.setEstimated(statEntry.get("estimated").get("value").asInt());
				vce.setCompleted(statEntry.get("completed").get("value").asInt());
			}

			return velocityChart;

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SprintList getSprints(int boardId) {

		String url = BASE_ROUTE + "agile/1.0/board/" + boardId + "/sprint";
		ResponseEntity<SprintList> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<SprintList>() {
					});

			SprintList sprints = response.getBody();
			return sprints;

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BoardList getBoards() {
		String url = BASE_ROUTE + "agile/1.0/board";
		ResponseEntity<BoardList> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<BoardList>() {
					});
			BoardList boards = response.getBody();
			return boards;
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<IssueType> getIssueTypes() {
		String url = BASE_ROUTE + "api/2/issuetype";
		ResponseEntity<List<IssueType>> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<List<IssueType>>() {
					});
			List<IssueType> issueTypes = response.getBody();
			return issueTypes;
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Priority> getPriorities() {
		String url = BASE_ROUTE + "api/2/priority";
		ResponseEntity<List<Priority>> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<List<Priority>>() {
					});
			List<Priority> priorities = response.getBody();
			return priorities;
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Status> getStatus() {
		String url = BASE_ROUTE + "api/2/status";
		ResponseEntity<List<Status>> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<List<Status>>() {
					});

			List<Status> status = response.getBody();
			return status;
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserJira> getUserJira(String email) {
		String url = BASE_ROUTE + "api/2/user/search?username=" + email;
		ResponseEntity<List<UserJira>> response = null;
		ResponseEntity<JsonNode> response1 = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<List<UserJira>>() {
					});
			
			response1 = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(USERNAME, PASSWORD)),
					new ParameterizedTypeReference<JsonNode>() {
					});
			ArrayNode jsonResponse = (ArrayNode) response1.getBody();
			Iterator<JsonNode> iterator = jsonResponse.elements();
			String avatarUrl = "";
			if(iterator.hasNext()) {
				JsonNode node = iterator.next();
				avatarUrl = node.get("avatarUrls").get("48x48").asText();
			}
			
			List<UserJira> user = response.getBody();
			user.get(0).setImg48x48(avatarUrl);
			
			return user;
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return null;
	}
}
