package de.interoberlin.sauvignon.model.smil;

import de.interoberlin.sauvignon.model.svg.transform.ATransformOperator;

public interface IAnimatable
{
	public ATransformOperator getTransformOperator(long millisSinceStart);
}
