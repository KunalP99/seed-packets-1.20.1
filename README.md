# Seed Packets — Player Guide

## What is this mod?

Seed Packets lets you combine vanilla seeds at a crafting table to create **seed packets** — special items that grow into unique crops with powerful food effects. The more seed types you mix, the stronger the result.

---

## How it works

1. **Craft a seed packet** using the recipes below — each craft gives you **1 packet with 5 uses**
2. **Plant the packet** by right-clicking on farmland — each use plants one crop
3. **Check your remaining uses** by hovering over the packet — the tooltip shows "X uses left"
4. **Wait for the crop to grow** through 8 stages (stage 0 → stage 7). Bone meal works to speed it up
5. **Break the fully grown crop** (stage 7) to harvest 1–2 food items and **1–4 uses returned** to your seed packet
6. **Eat the food** to restore hunger and gain a timed potion effect

> Breaking a crop before it is fully grown drops **nothing** — wait for stage 7.

> **Vinebloom is different** — see the Vinebloom section below for its melon-style behaviour.

---

## Uses & Combining Packets

- Each packet starts with **5 uses**
- A packet can hold up to **64 uses**
- **Combine packets of the same type** in any crafting grid to merge their uses — place any number of packets (2–9) anywhere in the grid and take the single combined result
- If the combined total would exceed 64, the overflow is **discarded** (e.g. filling a grid with thirteen 5-use packets gives a 64-use packet, not 65)
- The number of uses remaining is always shown in the item tooltip

---

## Progression Tiers

There are 3 custom tiers plus a special Scatter Packet.

| Tier | Packet | Seeds used | Hunger | Effect |
|------|--------|-----------|--------|--------|
| 1 | Wheatroot | 2 types | 3 | Speed I (10s) |
| 1 | Vinebloom | 2 types | 4 | Jump Boost I (12s) |
| 2 | Harvest | 3 types | 5 | Regeneration I (8s) |
| 2 | Golden Spud | 3 types | 5 | Strength I (15s) |
| 3 | Supreme Harvest | 4 types | 7 | Speed I (20s) + Strength I (20s) |
| — | Scatter | all 6 vanilla seeds | — | Plants a random vanilla crop |

---

## Recipes

Custom tier 1–3 packet recipes produce **1 packet with 5 uses**. The Scatter Packet starts with **15 uses**.

---

### Tier 1

#### Wheatroot Packet
```
[ Wheat Seeds ] [ Wheat Seeds ] [ Wheat Seeds ]
[Beetroot Seeds][Beetroot Seeds][Beetroot Seeds]
```
- **Grows:** Wheatroot Crop
- **Harvest:** Wheatroot (1–2)
- **Food effect:** +3 hunger | Speed I for 10 seconds

---

#### Vinebloom Packet
```
[Pumpkin Seeds][Pumpkin Seeds][Pumpkin Seeds]
[ Melon Seeds ][ Melon Seeds ][ Melon Seeds ]
```
- **Grows:** Vinebloom Stem (melon-style — see below)
- **Harvest:** Vinebloom (1–2) from the fruit block
- **Food effect:** +4 hunger | Jump Boost I for 12 seconds

**Vinebloom works differently from other packets:**
- The packet plants a **stem** on farmland (1 use per plant)
- The stem grows through 8 stages; when fully grown it **randomly spawns a Vinebloom fruit block** on an adjacent solid tile (farmland, dirt, grass, etc.)
- **Break the fruit** to get 1–2 Vinebloom food — no uses are returned
- The stem stays planted and will grow a new fruit over time
- **Break the stem** to uproot it — returns **1 use** to your packet
- Bone meal on the stem advances growth; bone meal on a mature stem spawns the fruit immediately

---

### Tier 2

#### Harvest Packet
```
[Wheat Seeds ][Beetroot Seeds]
[Beetroot Seeds][   Carrot   ]
[Wheat Seeds ][   Carrot   ]
```
- **Grows:** Harvest Crop
- **Harvest:** Harvest Crop food item (1–2)
- **Food effect:** +5 hunger | Regeneration I for 8 seconds

---

#### Golden Spud Packet
```
[Pumpkin Seeds][Pumpkin Seeds]
[   Carrot   ][   Potato    ]
[   Potato   ][   Carrot    ]
```
- **Grows:** Golden Spud Crop
- **Harvest:** Golden Spud (1–2)
- **Food effect:** +5 hunger | Strength I for 15 seconds

---

### Tier 3

#### Supreme Harvest Packet
```
[Wheat Seeds ][Beetroot Seeds]
[   Carrot   ][   Potato    ]
[Wheat Seeds ][Beetroot Seeds]
```
- **Grows:** Supreme Harvest Crop
- **Harvest:** Supreme Harvest (1–2)
- **Food effect:** +7 hunger | Speed I for 20 seconds + Strength I for 20 seconds

---

### Scatter

#### Scatter Packet
**Shapeless recipe** — place all 6 ingredients anywhere in the crafting grid:
- Wheat Seeds
- Melon Seeds
- Pumpkin Seeds
- Beetroot Seeds
- Carrot
- Potato

Each use plants a **random vanilla crop** on farmland — could be wheat, carrots, potatoes, beetroot, a melon stem, or a pumpkin stem. The planted crop behaves exactly like placing the vanilla seed/crop directly.

---

## Food Effects Summary

| Food | Hunger | Effect 1 | Effect 2 |
|------|--------|----------|----------|
| Wheatroot | 3 | Speed I (10s) | — |
| Vinebloom | 4 | Jump Boost I (12s) | — |
| Harvest Crop | 5 | Regeneration I (8s) | — |
| Golden Spud | 5 | Strength I (15s) | — |
| Supreme Harvest | 7 | Speed I (20s) | Strength I (20s) |

---

## Seed Storage Packet

Store large quantities of vanilla seeds in a single inventory slot. Holds up to **100 seeds**.

### Craft

```
P S P
S   S    P = Paper, S = String
P S P
```

### Accepted inputs
- **Vanilla seeds**: Wheat Seeds, Beetroot Seeds, Pumpkin Seeds, Melon Seeds, Carrot, Potato
- **Mod seed packets**: Wheatroot, Vinebloom, Harvest, Golden Spud, and Supreme Harvest packets — the stored count represents total uses

### Filling
Place the storage packet + same-type seeds (or mod packets) in the crafting grid.
- Vanilla seeds: each seed slot adds 1 to the count per craft
- Mod packets: each packet adds all of its current uses at once (the whole packet is consumed)

If the packet already contains something, you can only add the same type. Type mismatch = no recipe result.

### Emptying
Place the storage packet alone in the crafting grid.
- Vanilla seeds: up to 64 are returned per craft; remainder stays in the packet
- Mod packets: one packet is returned with up to 64 uses; remainder stays in the packet

Repeat until fully empty.

### Tooltip
Hovering over a storage packet shows: `Seed Name: N / Capacity` (or `Empty` if nothing is stored).

---

## Tips

- All crops need **farmland** (hoed dirt near water) and **light** to grow, just like vanilla crops
- **Bone meal** can be used on any seed packet crop to skip growth stages
- Crops drop **nothing** if broken early — always wait for stage 7
- A fully grown crop returns **1–4 random uses** to your packet — you can sustain your farm without crafting, though you'll still need to craft to build up a stockpile
- **Combine packets** to stockpile uses — dump a stack of fresh 5-use packets into the crafting grid at once to get a single combined packet
- The Scatter Packet is great for large farms where you don't mind a mix of vanilla crops
- Supreme Harvest is the strongest food in the mod — save it for combat or tough situations
- All packets and their food items can be found in the **Seed Packets** creative tab

---

## Recommended Mods

- **Model Gap Fix** — fixes visual gaps that can appear in item textures when held in hand, caused by a quirk in Minecraft's generated item model rendering. Highly recommended alongside this mod.
