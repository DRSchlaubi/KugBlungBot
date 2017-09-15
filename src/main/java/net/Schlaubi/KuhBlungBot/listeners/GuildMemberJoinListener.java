package net.Schlaubi.KuhBlungBot.listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;


public class GuildMemberJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        Member member = e.getMember();
        Guild guild = e.getGuild();
        GuildController gcon = new GuildController(guild);
        Role role = guild.getRoleById("318009901680951297");
        gcon.addRolesToMember(member, role).queue();
    }
}
