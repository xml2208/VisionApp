package com.xml.visionapp.data.services

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class MapAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        if (p0?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && p0.packageName == "com.google.android.apps.maps") {
            // Check if the "Start" button is visible and clickable
            val rootNode = rootInActiveWindow
            val startButtonNode = findStartButtonNode(rootNode)
            if (startButtonNode != null && startButtonNode.isClickable) {
                startButtonNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
    }

    override fun onInterrupt() {}

    private fun findStartButtonNode(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (node == null) return null
        for (i in 0 until node.childCount) {
            val childNode = node.getChild(i)
            if (childNode != null && childNode.className == "android.widget.Button" && childNode.text == "Start") {
                return childNode
            }
            val startButton = findStartButtonNode(childNode)
            if (startButton != null) {
                return startButton
            }
        }
        return null
    }
}