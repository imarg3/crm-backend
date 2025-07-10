package org.code.bluetick.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// @NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Profiles {
    /**
     * <p>Informs the DI Framework that the Object or Method in question is for testing purposes only.</p>
     * <p>Should <b>NEVER</b> be used in the `main` package.</p>
     */
    public static final String TEST = "test";

    /**
     * <p>Used to inform DI Framework that the Object or Method in question should not be
     *     included if the `TEST` Profile is active.</p>
     */
    public static final String NOT_TEST = "!" + TEST;
}
