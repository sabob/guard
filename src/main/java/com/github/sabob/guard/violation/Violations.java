package com.github.sabob.guard.violation;

import com.github.sabob.guard.utils.StringUtils;

import java.util.*;

public class Violations {

    protected Set<String> violationsIdTrackingSet = new HashSet();

    protected List<Violation> violationList = new ArrayList();

    protected Map<String, List<Violation>> violationListByName = new HashMap();

    public void merge(Violations violations) {

        if (violations == null) {
            throw new IllegalArgumentException("violations cannot be null");
        }

        addAll(violations.getList());
    }

    public void addAll(List<Violation> violationList) {

        if (violationList == null) {
            throw new IllegalArgumentException("violationList cannot be null!");
        }

        for (Violation violation : violationList) {
            add(violation);
        }

    }

    public boolean add(Violation violation) {
        if (violation == null) {
            throw new IllegalArgumentException("violation cannot be null!");
        }

        // Check if we have already added the violation
        String validationId = getUniqueId(violation);
        if (violationsIdTrackingSet.contains(validationId)) {
            return false;
        }

        // Add the validationId so we don't add it again
        violationsIdTrackingSet.add(validationId);

        String name = violation.getName();

        if (StringUtils.isNotBlank(name)) {

            List<Violation> violationList = getOrCreateViolationListForName(name);
            violationList.add(violation);
        }

        getList().add(violation);
        return true;
    }

    public boolean contains(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        return violationListByName.containsKey(name);
    }

    public List<Violation> getList(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name cannot be blank");
        }

        List<Violation> list = violationListByName.get(name);
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    public List<Violation> getList() {
        return violationList;
    }

    public boolean isValid() {
        return violationList.isEmpty();
    }

    public boolean isValid(String name) {
        List<Violation> list = getList(name);
        return list.isEmpty();
    }

    public boolean isInvalid() {
        return !isValid();
    }

    public boolean isInvalid(String name) {
        return !isValid(name);
    }

    public void clear(String name) {
        getList(name).clear();
    }

    public void clear() {
        getList().clear();
    }

    public Violations copy() {
        Violations copy = new Violations();
        copy.violationsIdTrackingSet = new HashSet<>(violationsIdTrackingSet);
        copy.violationList = new ArrayList<>(getList());
        copy.violationListByName = new HashMap<>(violationListByName);
        return copy;
    }

    public Violations copy( String name ) {
        Violations copy = new Violations();
        copy.violationsIdTrackingSet = new HashSet<>(violationsIdTrackingSet);
        copy.violationList = new ArrayList<>(getList(name));
        copy.violationListByName = new HashMap<>(violationListByName);
        return copy;
    }

    protected List<Violation> getOrCreateViolationListForName(String name) {
        List<Violation> violationList = violationListByName.get(name);
        if (violationList == null) {
            violationList = new ArrayList<>();
            violationListByName.put(name, violationList);
        }
        return violationList;
    }

    protected String getUniqueId(Violation violation) {
        String id = violation.getName() + '-' + violation.getCode() + '-' + violation.getMessage();
        return id;
    }

    @Override
    public String toString() {
        return "Violations{" +
                " valid = " + isValid() +
                ", violationCount = " + violationList.size() +
                " }";
    }
}
