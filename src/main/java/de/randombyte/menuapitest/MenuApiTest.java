package de.randombyte.menuapitest;

import com.gmail.socraticphoenix.sponge.menu.InputTypes;
import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuProperties;
import com.gmail.socraticphoenix.sponge.menu.builder.ButtonBuilder;
import com.gmail.socraticphoenix.sponge.menu.builder.ButtonPageBuilder;
import com.gmail.socraticphoenix.sponge.menu.builder.MenuBuilder;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

@Plugin(id = "menu-api-test", name = "MenuApiTest")
public class MenuApiTest {
    @Listener
    public void onInit(GameInitializationEvent event) {
        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .executor((src, args) -> {
                    if (!(src instanceof Player)) throw new CommandException(Text.EMPTY);
                    Player player = (Player) src;

                    MenuBuilder builder = Menu.builder(this).properties(new MenuProperties(false, false));
                    ButtonPageBuilder pageBuilder = builder.buttonPage();
                    pageBuilder.id("page1").title(Text.of("Inventory Menu"));
                    ButtonBuilder button = pageBuilder.button();
                    button.id("button1").title(Text.of("A Button")).icon(ItemTypes.GUNPOWDER).tracker((map, menuEvent) -> {
                        menuEvent.player().sendMessage(Text.of("Achievement Get: Test MenuAPI"));
                    }, "tracker1").finish();
                    pageBuilder.orderedGridFormatter(false).finish(InputTypes.INVENTORY_BUTTON);
                    builder.build().send(player);

                    return CommandResult.success();
                })
                .build(), "test");
    }
}