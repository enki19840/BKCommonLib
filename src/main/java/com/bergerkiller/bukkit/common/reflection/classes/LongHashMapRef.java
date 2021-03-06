package com.bergerkiller.bukkit.common.reflection.classes;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.server.v1_4_6.LongHashMap;

import com.bergerkiller.bukkit.common.reflection.FieldAccessor;
import com.bergerkiller.bukkit.common.reflection.SafeField;

public class LongHashMapRef {
	private static final FieldAccessor<Object[]> entriesField = new SafeField<Object[]>(LongHashMap.class, "entries");
	private static final FieldAccessor<Integer> countField = new SafeField<Integer>(LongHashMap.class, "count");

	public static Collection<Object> getValues(LongHashMap instance) {
		Object[] entries = getEntries(instance);
		ArrayList<Object> values = new ArrayList<Object>(entries.length);
		for (int i = 0; i < entries.length; i++) {
			if (entries[i] != null) {
				values.add(LongHashMapEntryRef.entryValue.get(entries[i]));
			}
		}
		return values;
	}

	public static Object[] getEntries(LongHashMap instance) {
		return entriesField.get(instance);
	}

	public static void setEntries(LongHashMap instance, Object[] entries) {
		entriesField.set(instance, entries);
		countField.set(instance, entries.length);
	}
}
