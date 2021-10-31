package love.marblegate.jeb;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lekavar.lma.drinkbeer.recipes.BrewingRecipe;
import lekavar.lma.drinkbeer.registries.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class JEIBrewingRecipe implements IRecipeCategory<BrewingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(JEB.MODID, "brewing");
    private IDrawable background;
    private IDrawable icon;

    public JEIBrewingRecipe(IGuiHelper helper) {
        background = helper.createDrawable(new ResourceLocation(JEB.MODID, "textures/gui/brewing_gui.png"),
                0, 0, 175, 69);
        icon = helper.createDrawableIngredient(new ItemStack(ItemRegistry.BEER_MUG.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends BrewingRecipe> getRecipeClass() {
        return BrewingRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.get("just_enough_beer.jei.title.brewing");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(BrewingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredient());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BrewingRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 27, 15);
        recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        recipeLayout.getItemStacks().init(1, true, 45, 15);
        recipeLayout.getItemStacks().set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));

        recipeLayout.getItemStacks().init(2, true, 27, 33);
        recipeLayout.getItemStacks().set(2, ingredients.getInputs(VanillaTypes.ITEM).get(2));

        recipeLayout.getItemStacks().init(3, true, 45, 33);
        recipeLayout.getItemStacks().set(3, ingredients.getInputs(VanillaTypes.ITEM).get(3));

        recipeLayout.getItemStacks().init(4, true, 72, 39);
        recipeLayout.getItemStacks().set(4, recipe.geBeerCup());

        recipeLayout.getItemStacks().init(5, false, 127, 23);
        recipeLayout.getItemStacks().set(5, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    @Override
    public List<ITextComponent> getTooltipStrings(BrewingRecipe recipe, double mouseX, double mouseY) {
        List<ITextComponent> tooltips = new ArrayList<>();
        tooltips.add(new TranslationTextComponent("just_enough_beer.jei.tooltip.brewing").withStyle(TextFormatting.BLUE));
        int brewingTimeMin = (recipe.getBrewingTime() / 20) / 60;
        int brewingTimeSec = recipe.getBrewingTime() / 20 - brewingTimeMin * 60;
        tooltips.add(new StringTextComponent(brewingTimeMin + ":" + brewingTimeSec).withStyle(TextFormatting.BLUE));
        return tooltips;
    }
}
