package org.example.filter;

import org.example.translated.Rune;

public abstract class Filter {

    private boolean innate;

    public abstract boolean isRuneEligible(Rune rune);

    public class ProcFilter extends Filter {

        @Override
        public boolean isRuneEligible(Rune rune) {
            return false;
        }
    }

    public class RelativeEfficiencyFilter extends Filter {

        @Override
        public boolean isRuneEligible(Rune rune) {
            return false;
        }
    }

    public class SubstatPresenceFilter extends Filter {

        @Override
        public boolean isRuneEligible(Rune rune) {
            return false;
        }
    }
}
