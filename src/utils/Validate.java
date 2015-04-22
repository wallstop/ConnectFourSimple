package utils;

public final class Validate
{
    
    public static void isTrue(final boolean assertion)
    {
        isTrue(assertion, "Validated assertion was false");
    }
    
    public static void isTrue(final boolean assertion, final String message)
    {
        if(assertion == false)
        {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void notNull(final Object argument)
    {
        notNull(argument, "Validated oject was null");
    }

    public static void notNull(final Object argument, final String message)
    {
        if (argument == null)
        {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void inRange(final Number value, final Number min,
            final Number max)
    {
        notNull(value);
        notNull(min);
        notNull(max);
        if (value.doubleValue() < min.doubleValue()
                || value.doubleValue() > max.doubleValue())
        {
            throw new IllegalArgumentException(String.format(
                    "%d was not within [%d, %d]", value, min, max));
        }
    }
}
