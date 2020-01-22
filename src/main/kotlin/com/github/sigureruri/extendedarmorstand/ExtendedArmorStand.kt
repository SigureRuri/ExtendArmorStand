package com.github.sigureruri.extendedarmorstand

import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerArmorStandManipulateEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.plugin.java.JavaPlugin

class ExtendedArmorStand : JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler(ignoreCancelled = true)
    fun onClickAtArmorStand(event: PlayerInteractAtEntityEvent) {
        val armorStand = event.rightClicked as? ArmorStand ?: return
        val player = event.player

        if (player.isSneaking) {
            val playerInventory = player.inventory
            val armorStandEquipment = armorStand.equipment!!
            val playerItem = playerInventory.itemInMainHand
            val armorStandItem = armorStandEquipment.itemInMainHand

            playerInventory.setItemInMainHand(armorStandItem)
            armorStandEquipment.setItemInMainHand(playerItem)
            event.isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onArmorStandDamageByPlayer(event: EntityDamageByEntityEvent) {
        val player = event.damager as? Player ?: return
        val armorStand = event.entity as? ArmorStand ?: return

        if (player.isSneaking) {
            val playerInventory = player.inventory
            val armorStandEquipment = armorStand.equipment!!
            val playerItem = playerInventory.itemInMainHand
            val armorStandItem = armorStandEquipment.itemInOffHand

            playerInventory.setItemInMainHand(armorStandItem)
            armorStandEquipment.setItemInOffHand(playerItem)
            event.isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onArmorStandManipulateEvent(event: PlayerArmorStandManipulateEvent) {
        val playerItem = event.playerItem
        val armorStandItem = event.armorStandItem
        if ((playerItem.type != Material.AIR && armorStandItem.type == Material.AIR) || (playerItem.type != Material.AIR && armorStandItem.type != Material.AIR)) {
            if (event.player.isSneaking) event.isCancelled = true
        }
    }
}