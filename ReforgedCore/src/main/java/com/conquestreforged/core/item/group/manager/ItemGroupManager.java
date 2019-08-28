package com.conquestreforged.core.item.group.manager;

import com.conquestreforged.core.item.group.ConquestItemGroup;
import com.google.common.collect.Sets;
import net.minecraft.item.ItemGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ItemGroupManager {

    private static final ItemGroupManager instance = new ItemGroupManager();
    private static final Set<ItemGroup> required = Sets.newHashSet(ItemGroup.SEARCH, ItemGroup.INVENTORY, ItemGroup.HOTBAR);

    private final Map<Class<?>, Set<ItemGroup>> conquestGroups = new HashMap<>();
    private final Map<GroupType, Set<ItemGroup>> groups = new EnumMap<>(GroupType.class);

    private ItemGroupManager() {
        for (GroupType type : GroupType.values()) {
            groups.put(type, new HashSet<>());
        }
    }

    public void init() {
        storeVanillaGroups();
        storeModGroups();
    }

    public void register(ConquestItemGroup group) {
        conquestGroups.computeIfAbsent(group.getClass(), k -> new HashSet<>()).add(group);
        groups.put(GroupType.CONQUEST, conquestGroups.get(group.getClass()));
    }

    public void setConquestType(Class<? extends ConquestItemGroup> type) {
        Set<ItemGroup> groupList = conquestGroups.getOrDefault(type, Collections.emptySet());
        groups.put(GroupType.CONQUEST, groupList);
    }

    public void setVisibleItemGroups(GroupType... types) {
        List<ItemGroup> order = new ArrayList<>();

        for (GroupType type : types) {
            Set<? extends ItemGroup> groupList = groups.get(type);
            order.addAll(sorted(groupList));
        }

        required.forEach(group -> addRequired(order, group));

        ItemGroup.GROUPS = new ItemGroup[0];
        for (int i = 0; i < order.size(); i++) {
            ItemGroup group = order.get(i);
            if (group == null) {
                new EmptyGroup();
            } else if (required.contains(group)) {
                addGroupSafe(i, group);
            } else {
                new DelegateGroup(group);
            }
        }
    }


    private void storeVanillaGroups() {
        Set<ItemGroup> groups = this.groups.get(GroupType.VANILLA);
        for (Field field : ItemGroup.class.getFields()) {
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == ItemGroup.class) {
                try {
                    Object value = field.get(null);
                    ItemGroup group = (ItemGroup) value;
                    groups.add(group);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void storeModGroups() {
        Set<ItemGroup> vanilla = this.groups.get(GroupType.VANILLA);
        Set<ItemGroup> other = this.groups.get(GroupType.OTHER);
        for (ItemGroup group : ItemGroup.GROUPS) {
            if (vanilla.contains(group)) {
                continue;
            }
            if (group instanceof ConquestItemGroup || group instanceof DelegateGroup) {
                continue;
            }
            other.add(group);
        }
    }

    private void addRequired(List<ItemGroup> groups, ItemGroup required) {
        int index = groups.indexOf(required);
        if (index == required.getIndex()) {
            return;
        }

        if (index != -1) {
            groups.remove(index);
        }

        while (groups.size() <= required.getIndex()) {
            groups.add(null);
        }

        groups.set(required.getIndex(), required);
    }

    public static ItemGroupManager getInstance() {
        return instance;
    }

    private static List<ItemGroup> sorted(Collection<? extends ItemGroup> groups) {
        List<ItemGroup> list = new ArrayList<>(groups);
        list.sort(ItemGroupManager::sort);
        return list;
    }

    private static int sort(ItemGroup g1, ItemGroup g2) {
        if (g1 instanceof ConquestItemGroup && g2 instanceof ConquestItemGroup) {
            return ((ConquestItemGroup) g1).getOriginalIndex() - ((ConquestItemGroup) g2).getOriginalIndex();
        }
        return g1.getIndex() - g2.getIndex();
    }

    private static synchronized int addGroupSafe(int index, ItemGroup newGroup) {
        if (index == -1) {
            index = ItemGroup.GROUPS.length;
        }
        if (index >= ItemGroup.GROUPS.length) {
            ItemGroup[] tmp = new ItemGroup[index + 1];
            System.arraycopy(ItemGroup.GROUPS, 0, tmp, 0, ItemGroup.GROUPS.length);
            ItemGroup.GROUPS = tmp;
        }
        ItemGroup.GROUPS[index] = newGroup;
        return index;
    }
}
