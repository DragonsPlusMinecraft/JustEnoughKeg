package love.marblegate.jeb;

import lekavar.lma.drinkbeer.gui.BeerBarrelContainer;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

//TODO Finish this
public class BrewRecipeTransferHandler implements IRecipeTransferHandler<BeerBarrelContainer> {
    @Override
    public Class<BeerBarrelContainer> getContainerClass() {
        return BeerBarrelContainer.class;
    }

    @Nullable
    @Override
    public IRecipeTransferError transferRecipe(BeerBarrelContainer container, Object recipe, IRecipeLayout recipeLayout, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
        return IRecipeTransferHandler.super.transferRecipe(container, recipe, recipeLayout, player, maxTransfer, doTransfer);
    }
}
