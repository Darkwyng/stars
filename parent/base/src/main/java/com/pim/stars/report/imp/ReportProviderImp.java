package com.pim.stars.report.imp;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

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

	@Override
	public Stream<MessageReport> getReports(final Game game, final Race race, final Locale locale) {
		return reportRepository.findByGameIdAndYearAndRaceId(game.getId(), game.getYear(), race.getId()).stream()
				.map(entity -> new MessageReportImp(entity, locale));
	}

	protected ResourceBundle getBundle(final String bundleName, final Locale locale) {
		return ResourceBundle.getBundle(bundleName, locale); // TODO 5: bad error handling; this may raise an exception if the bundle does not exist
	}

	private class MessageReportImp implements MessageReport {

		private final ReportEntity entity;
		private final Locale locale;

		public MessageReportImp(final ReportEntity entity, final Locale locale) {
			this.entity = entity;
			this.locale = locale;
		}

		@Override
		public String getType() {
			return entity.getReportClassName();
		}

		@Override
		public String getMessage() {
			final ResourceBundle bundle = getBundle(entity.getBundleName(), locale);
			final String pattern = bundle.getString(entity.getReportClassName());
			return MessageFormat.format(pattern, entity.getArguments().toArray());
		}
	}
}