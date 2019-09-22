package cliq.report;

import gate.entity.Org;
import gate.report.Image;
import gate.report.Report;
import java.time.LocalDateTime;

public class CliQReport extends Report
{

	private static final Image DEFAULT
		= Image.of(CliQReport.class.getResource("Logo.png"));

	public CliQReport(Org org)
	{
		if (org != null && org.getIcon() != null)
			addImage(org.getIcon());
		else
			add(DEFAULT);
	}

	public CliQReport(Org org, Orientation orientation)
	{
		super(orientation);

		if (org != null && org.getIcon() != null)
			addImage(org.getIcon());
		else
			add(DEFAULT);

		addHeader(LocalDateTime.now());
		if (org != null && org.getName() != null)
			addHeader(org.getName());
	}
}
