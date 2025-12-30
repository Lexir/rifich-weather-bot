package ru.salex.weather.bot.dto;

import ru.salex.weather.bot.domain.*;

import java.util.List;

public final class MessageView {

    private static final double KELVIN_OFFSET = 273.15;

    private MessageView() {
    }

    public static String render(
            GeocodingResponse.Location location,
            List<ForecastSlice> slices
    ) {
        StringBuilder sb = new StringBuilder();

        header(sb, location);

        for (ForecastSlice slice : slices) {
            timeBlock(sb, slice);
        }

        footer(sb);
        return sb.toString();
    }

    private static void header(
            StringBuilder sb,
            GeocodingResponse.Location loc
    ) {
        sb.append("🌬 <b>Прогноз Windy</b>\n")
                .append("📍 ")
                .append(loc.name())
                .append(", ")
                .append(loc.country())
                .append("\n\n");
    }

    private static void timeBlock(
            StringBuilder sb,
            ForecastSlice slice
    ) {
        sb.append("⏱ <b>")
                .append(slice.label())
                .append("</b>\n");

        for (Level level : Level.values()) {
            levelRow(sb, slice, level);
        }

        sb.append("\n");
    }

    private static void levelRow(
            StringBuilder sb,
            ForecastSlice slice,
            Level level
    ) {
        sb.append(level.label()).append(": ");

        // 🌡 Температура
        slice.temperature()
                .at(level)
                .ifPresent(t ->
                        sb.append(String.format(
                                "🌡 %.1f°C ",
                                t - KELVIN_OFFSET
                        ))
                );

        // 💧 Влажность
        slice.humidity()
                .at(level)
                .ifPresent(h ->
                        sb.append(String.format(
                                "💧 %.0f%% ",
                                h
                        ))
                );

        // 💨 Ветер (по поверхности, одинаков для всех уровней)
        Wind wind = slice.wind();
        if (wind != null) {
            sb.append(String.format(
                    "💨 %.1f м/с %s ",
                    wind.speed(),
                    WindDirection.arrow(wind.direction())
            ));
        }

        // 🌧 Осадки
        Precipitation p = slice.precipitation();
        if (p != null && p.isPresent()) {
            sb.append(String.format(
                    "🌧 %.1f мм ",
                    p.mm()
            ));
        }

        sb.append("\n");
    }


    private static void footer(StringBuilder sb) {
        sb.append("🕒 Время: UTC\n")
                .append("📊 Источник: Windy (iconEU)");
    }
}

