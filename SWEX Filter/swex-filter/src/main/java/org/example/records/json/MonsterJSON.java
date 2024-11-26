package org.example.records.json;

import java.util.Collection;

public record MonsterJSON(int id, Collection<RuneJSON> runesJSON) {
}
