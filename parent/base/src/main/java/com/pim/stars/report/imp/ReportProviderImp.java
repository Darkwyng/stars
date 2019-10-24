package com.pim.stars.report.imp;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.report.api.ReportProvider;
import com.pim.stars.report.imp.persistence.ReportEntity;
import com.pim.stars.report.imp.persistence.ReportRepository;
import com.pim.stars.turn.api.Race;

@Component
public class ReportProviderImp implements ReportProvider {

	@Autowired
	private ReportRepository reportRepository;

	@PostConstruct
	public void postConstruct() {
		// To fail early (if necessary), the bundle is loaded while the application context is starting up:
		getBundle(Locale.CHINA);
	}

	@Override
	public Stream<MessageReport> getReports(final Game game, final Race race, final Locale locale) {
		final ResourceBundle bundle = getBundle(locale);
		return reportRepository.findByGameIdAndYearAndRaceId(game.getId(), game.getYear(), race.getId()).stream()
				.map(entity -> new MessageReportImp(entity, bundle));
	}

	protected ResourceBundle getBundle(final Locale locale) {
		return ResourceBundle.getBundle("com.pim.stars.report.imp.messages", locale); // TODO: bundle name is part of API
	}

	private class MessageReportImp implements MessageReport {

		private final ReportEntity entity;
		private final ResourceBundle bundle;

		public MessageReportImp(final ReportEntity entity, final ResourceBundle bundle) {
			this.entity = entity;
			this.bundle = bundle;
		}

		@Override
		public String getType() {
			return entity.getReportClassName();
		}

		@Override
		public String getMessage() {
			final String pattern = bundle.getString(entity.getReportClassName());
			return MessageFormat.format(pattern, entity.getArguments().toArray());
		}
	}
}
