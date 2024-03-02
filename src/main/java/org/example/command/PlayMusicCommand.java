package org.example.command;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;
import org.example.Audio.AudioPlayerSendHandler;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import org.example.Audio.TrackScheduler;

import java.util.List;

public class PlayMusicCommand {
	private final String name = "play";

	//	@Override
	public CommandData getCommandData() {
		return Commands.slash("play", "Play music")
				.addOption(OptionType.STRING, "music-link", "включить музыку", true, false);
	}

	//	@Override
	public String getName() {
		return name;
	}

	//	@Override
	public String getDescription() {
		return null;
	}

	//	@Override
	public void execute(SlashCommandInteractionEvent event) {
		if (!event.isFromGuild()) return;

		VoiceChannel voiceChannel;
		Member member = event.getMember();
		Guild guild = event.getGuild();


//			if (member != null) {
//				voiceChannel = member.getVoiceState().getChannel().asVoiceChannel();
//			} else {
//				voiceChannel = guild.getVoiceChannelById("1194337591449878710");
//			}
		voiceChannel = guild.getVoiceChannelById("1194337591449878710");

		AudioManager audioManager = guild.getAudioManager();

		AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioPlayer player = playerManager.createPlayer();
		TrackScheduler trackScheduler = new TrackScheduler(player);
		player.addListener(trackScheduler);

		event.reply("play music").queue();
		String identifier = event.getOptionsByName("music-link").get(0).getAsString();
		playerManager.loadItem(identifier, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				trackScheduler.queue(track);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				for (AudioTrack track : playlist.getTracks()) {
					trackScheduler.queue(track);
				}
			}

			@Override
			public void noMatches() {
				// Notify the user that we've got nothing
			}

			@Override
			public void loadFailed(FriendlyException throwable) {
				// Notify the user that everything exploded
			}
		});

		audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
		audioManager.openAudioConnection(voiceChannel);
	}

//	@Override
//	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
//		if (event.getName().equals("play")) {
//			event.reply("anus").addActionRow(
//					Button.secondary("button", "Text"),
//					Button.success("emoji", Emoji.fromFormatted("<:minn:245267426227388416>"))
//			).queue();
//		}
//	}
//
//	@Override
//	public void onButtonInteraction(ButtonInteractionEvent event) {
//		if (event.getComponentId().equals("button")) {
//			event.reply("Hello :)").queue();
//		} else if (event.getComponentId().equals("emoji")) {
//			event.editMessage("That button didn't say click me").queue(); // update the message
//		}
//	}

	//	@Override
	public List<OptionData> getOptions() {
		return null;
	}
}
