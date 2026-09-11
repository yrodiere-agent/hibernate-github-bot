package org.hibernate.infra.bot.tests;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.io.IOException;

import org.kohsuke.github.GHCheckRun;
import org.kohsuke.github.GHCheckRunBuilder;
import org.kohsuke.github.GHRepository;
import org.mockito.Answers;

abstract class AbstractPullRequestTest {
	final GHCheckRunBuilder contributionRulesCheckRunCreateBuilderMock = mockCheckRunBuilder();
	final GHCheckRunBuilder contributionRulesCheckRunUpdateBuilderMock = mockCheckRunBuilder();

	GHCheckRunBuilder mockCheckRunBuilder() {
		return mock( GHCheckRunBuilder.class, withSettings().defaultAnswer( Answers.RETURNS_SELF ) );
	}

	void mockCheckRuns(GHRepository repoMock, String headSHA) throws IOException {
		GHCheckRun contributionRulesCheckRunMock = mock( GHCheckRun.class );
		mockCreateCheckRun( repoMock, "Contribution rules", headSHA,
				contributionRulesCheckRunCreateBuilderMock, contributionRulesCheckRunMock, 42L
		);
		mockUpdateCheckRun( repoMock, 42L, contributionRulesCheckRunUpdateBuilderMock, contributionRulesCheckRunMock );
	}

	void mockCreateCheckRun(GHRepository repoMock, String name, String headSHA,
			GHCheckRunBuilder checkRunBuilderMock, GHCheckRun checkRunMock, long checkRunId) throws IOException {
		lenient().when( repoMock.createCheckRun( name, headSHA ) ).thenReturn( checkRunBuilderMock );
		lenient().when( checkRunMock.getId() ).thenReturn( checkRunId );
		lenient().when( checkRunBuilderMock.create() ).thenReturn( checkRunMock );
	}

	void mockUpdateCheckRun(GHRepository repoMock, long checkRunId,
			GHCheckRunBuilder checkRunBuilderMock, GHCheckRun checkRunMock) throws IOException {
		lenient().when( repoMock.updateCheckRun( checkRunId ) ).thenReturn( checkRunBuilderMock );
		lenient().when( checkRunBuilderMock.create() ).thenReturn( checkRunMock );
	}

}
