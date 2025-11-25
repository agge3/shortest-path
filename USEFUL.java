// worldpoint override?
@Subscribe
public void onPluginMessage(PluginMessage event) {
    if (!CONFIG_GROUP.equals(event.getNamespace())) {
        return;
    }

    String action = event.getName();
    if (PLUGIN_MESSAGE_PATH.equals(action)) {
        Map<String, Object> data = event.getData();
        Object objStart = data.getOrDefault(PLUGIN_MESSAGE_START, null);
        Object objTarget = data.getOrDefault(PLUGIN_MESSAGE_TARGET, null);
        Object objConfigOverride = data.getOrDefault(PLUGIN_MESSAGE_CONFIG_OVERRIDE, null);

        @SuppressWarnings("unchecked")
        Map<String, Object> configOverride = (objConfigOverride instanceof Map<?,?>) ? ((Map<String, Object>) objConfigOverride) : null;
        if (configOverride != null && !configOverride.isEmpty()) {
            this.configOverride.clear();
            for (String key : configOverride.keySet()) {
                this.configOverride.put(key, configOverride.get(key));
            }
            cacheConfigValues();
        }

        if (objStart == null && objTarget == null) {
            return;
        }

        int start = (objStart instanceof WorldPoint) ? WorldPointUtil.packWorldPoint((WorldPoint) objStart)
            : ((objStart instanceof Integer) ? ((int) objStart) : WorldPointUtil.UNDEFINED);
        if (start == WorldPointUtil.UNDEFINED) {
            if (client.getLocalPlayer() == null) {
                return;
            }
            start = WorldPointUtil.packWorldPoint(client.getLocalPlayer().getWorldLocation());
        }

        Set<Integer> targets = new HashSet<>();
        if (objTarget instanceof Integer) {
            int packedPoint = (Integer) objTarget;
            if (packedPoint == WorldPointUtil.UNDEFINED) {
                return;
            }
            targets.add(packedPoint);
        } else if (objTarget instanceof WorldPoint) {
            int packedPoint = WorldPointUtil.packWorldPoint((WorldPoint) objTarget);
            if (packedPoint == WorldPointUtil.UNDEFINED) {
                return;
            }
            targets.add(packedPoint);
        } else if (objTarget instanceof Set<?>) {
            @SuppressWarnings("unchecked")
            Set<Object> objTargets = (Set<Object>) objTarget;
            for (Object obj : objTargets) {
                int packedPoint = WorldPointUtil.UNDEFINED;
                if (obj instanceof Integer) {
                    packedPoint = (Integer) obj;
                } else if (obj instanceof WorldPoint) {
                    packedPoint = WorldPointUtil.packWorldPoint((WorldPoint) obj);
                }
                if (packedPoint == WorldPointUtil.UNDEFINED) {
                    return;
                }
                targets.add(packedPoint);
            }
        }

        boolean useOld = targets.isEmpty() && pathfinder != null;
        restartPathfinding(start, useOld ? pathfinder.getTargets() : targets, useOld);
    } else if (PLUGIN_MESSAGE_CLEAR.equals(action)) {
        this.configOverride.clear();
        cacheConfigValues();
        setTarget(WorldPointUtil.UNDEFINED);
    }
}

    private void setTargets(Set<Integer> targets, boolean append) {
        if (targets == null || targets.isEmpty()) {
            synchronized (pathfinderMutex) {
                if (pathfinder != null) {
                    pathfinder.cancel();
                }
                pathfinder = null;
            }

            worldMapPointManager.removeIf(x -> x == marker);
            marker = null;
            startPointSet = false;
        } else {
            Player localPlayer = client.getLocalPlayer();
            if (!startPointSet && localPlayer == null) {
                return;
            }
            worldMapPointManager.removeIf(x -> x == marker);
            if (targets.size() == 1) {
                marker = new WorldMapPoint(WorldPointUtil.unpackWorldPoint(targets.iterator().next()), MARKER_IMAGE);
                marker.setName("Target");
                marker.setTarget(marker.getWorldPoint());
                marker.setJumpOnClick(true);
                worldMapPointManager.add(marker);
            }

            int start = WorldPointUtil.fromLocalInstance(client, localPlayer.getLocalLocation());
            lastLocation = start;
            if (startPointSet && pathfinder != null) {
                start = pathfinder.getStart();
            }
            Set<Integer> destinations = new HashSet<>(targets);
            if (pathfinder != null && append) {
                destinations.addAll(pathfinder.getTargets());
            }
            restartPathfinding(start, destinations, append);
        }
    }


    private void setTargets(Set<Integer> targets, boolean append) {
        if (targets == null || targets.isEmpty()) {
            synchronized (pathfinderMutex) {
                if (pathfinder != null) {
                    pathfinder.cancel();
                }
                pathfinder = null;
            }

            worldMapPointManager.removeIf(x -> x == marker);
            marker = null;
            startPointSet = false;
        } else {
            Player localPlayer = client.getLocalPlayer();
            if (!startPointSet && localPlayer == null) {
                return;
            }
            worldMapPointManager.removeIf(x -> x == marker);
            if (targets.size() == 1) {
                marker = new WorldMapPoint(WorldPointUtil.unpackWorldPoint(targets.iterator().next()), MARKER_IMAGE);
                marker.setName("Target");
                marker.setTarget(marker.getWorldPoint());
                marker.setJumpOnClick(true);
                worldMapPointManager.add(marker);
            }

            int start = WorldPointUtil.fromLocalInstance(client, localPlayer.getLocalLocation());
            lastLocation = start;
            if (startPointSet && pathfinder != null) {
                start = pathfinder.getStart();
            }
            Set<Integer> destinations = new HashSet<>(targets);
            if (pathfinder != null && append) {
                destinations.addAll(pathfinder.getTargets());
            }
            restartPathfinding(start, destinations, append);
        }
    }


// SKILL LEVELS
            for (; i < Skill.values().length; i++) {
                boostedSkillLevelsAndMore[i] = client.getBoostedSkillLevel(Skill.values()[i]);
            }
            boostedSkillLevelsAndMore[i++] = client.getTotalLevel(); // skill total level
            boostedSkillLevelsAndMore[i++] = getCombatLevel(); // combat level
            boostedSkillLevelsAndMore[i++] = client.getVarpValue(VarPlayerID.QP); // quest points
