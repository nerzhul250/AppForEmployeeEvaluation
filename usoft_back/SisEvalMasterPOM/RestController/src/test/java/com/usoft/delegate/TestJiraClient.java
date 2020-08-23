package com.usoft.delegate;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.usoft.dtos.jira.BoardList;
import com.usoft.dtos.jira.IssueType;
import com.usoft.dtos.jira.Priority;
import com.usoft.dtos.jira.SprintList;
import com.usoft.dtos.jira.SprintReport;
import com.usoft.dtos.jira.Status;
import com.usoft.dtos.jira.UserJira;
import com.usoft.dtos.jira.VelocityChartElement;

class TestJiraClient {

	JiraApiRestClient jira = new JiraApiRestClient();

	@Test
	void testGetSprintReport() {

		SprintReport sr = jira.getSprintReport(18, 59);
		System.out.println(">> Sprint Report - Test " + sr.getUserWhoStarted());
		System.out.println(">>> Sprint Report - Test - assignee: " + sr.getContents().getCompletedIssues().get(0).getAssignee());
		if (sr != null) {
			assertTrue(true);
		}
	}

	@Test
	void testGetSprints() {
		SprintList response = jira.getSprints(18);

		System.out.println(">> Getting Sprints: " + response.getValues().size() + " :: " + response.getValues().get(0).getName());
		if (response != null) {
			assertTrue(true);
		}
	}

	@Test
	void testGetBoards() {
		BoardList response = jira.getBoards();
		System.out.println(">> Getting Boards: " + response.getValues().get(0).getName());
		if (response != null) {
			assertTrue(true);
		}
	}

	@Test
	void testGetIssueTypes() {
		List<IssueType> response = jira.getIssueTypes();
		System.out.println(">> Getting IssueTypes: " + response.get(0).getName());
		if (response != null) {
			assertTrue(true);
		}
	}

	@Test
	void testGetPriorities() {
		List<Priority> response = jira.getPriorities();
		System.out.println(">> getting priorities: " + response.get(0).getName());
		if (response != null) {
			assertTrue(true);
		}
	}

	@Test
	void testGetStatus() {
		List<Status> response = jira.getStatus();
		System.out.println(">> getting status: " + response.get(0).getName());
		if (response != null) {
			assertTrue(true);
		}
	}

	@Test
	void testGetVelocityChart() {
		List<VelocityChartElement> velocityChart = jira.getVelocityChart(18);
		System.out.println(velocityChart.get(0).getSprint().getName() + ": " + " Estimated: "
				+ velocityChart.get(0).getEstimated() + " Completed: " + velocityChart.get(0).getCompleted());
		if (velocityChart != null) {
			assertTrue(true);
		}
	}
	@Test
	void testGetUserJira() {
		List<UserJira> response = jira.getUserJira("leonardo.franco@correo.icesi.edu.co");
		System.out.println(">> getting user: "+response.get(0).getKey()+" :: avatarUrl: "+response.get(0).getImg48x48());
		if(response!=null) {
			assertTrue(true);
		}
	}

}
