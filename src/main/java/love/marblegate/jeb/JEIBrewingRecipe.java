package love.marblegate.jeb;

import com.mojang.blaze3d.vertex.PoseStack;
import lekavar.lma.drinkbeer.recipes.BrewingRecipe;
import lekavar.lma.drinkbeer.registries.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JEIBrewingRecipe implements IRecipeCategory<BrewingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(JEB.MODID, "brewing");
    private static final String DRINK_BEER_YELLOW = "#F4D223";
    private static final String NIGHT_HOWL_CUP_HEX_COLOR = "#C69B82";
    private static final String PUMPKIN_DRINK_CUP_HEX_COLOR = "#AC6132";
    private final IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;


    public JEIBrewingRecipe(IGuiHelper helper) {
        guiHelper = helper;
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
    public Component getTitle() {
        return new TranslatableComponent("just_enough_keg.jei.title.brewing");
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
        ingredients.setInputIngredients(recipe.getIngredients());
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

        recipeLayout.getItemStacks().init(4, false, 127, 23);
        recipeLayout.getItemStacks().set(4, ingredients.getOutputs(VanillaTypes.ITEM).get(0));

        recipeLayout.moveRecipeTransferButton(156, 50);
    }

    @Override
    public void draw(BrewingRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
        IDrawable drawable = guiHelper.createDrawableIngredient(recipe.getBeerCup());
        drawable.draw(stack, 73, 40);
    }

    @Override
    public List<Component> getTooltipStrings(BrewingRecipe recipe, double mouseX, double mouseY) {
        List<Component> tooltips = new ArrayList<>();
        if (!inTransferBottomRange(mouseX, mouseY)){
            if (inCupSlotRange(mouseX, mouseY)) {
                tooltips.add(new TranslatableComponent("just_enough_keg.jei.tooltip.cup_slot")
                        .setStyle(Style.EMPTY.withColor(TextColor.parseColor(DRINK_BEER_YELLOW))));
                tooltips.add(new TranslatableComponent("just_enough_keg.jei.tooltip.cup_1")
                        .setStyle(Style.EMPTY.withColor(TextColor.parseColor(NIGHT_HOWL_CUP_HEX_COLOR)))
                        .append(new TextComponent(recipe.getRequiredCupCount() + " ")
                                .withStyle(Style.EMPTY.withBold(true).withColor(TextColor.parseColor(DRINK_BEER_YELLOW))))
                        .append(new TranslatableComponent(recipe.getBeerCup().getItem().getDescriptionId())
                                .withStyle(ChatFormatting.WHITE))
                        .append((new TranslatableComponent("just_enough_keg.jei.tooltip.cup_2")
                                .setStyle(Style.EMPTY.withColor(TextColor.parseColor(NIGHT_HOWL_CUP_HEX_COLOR))))));
            } else {
                int brewingTimeMin = (recipe.getBrewingTime() / 20) / 60;
                int brewingTimeSec = recipe.getBrewingTime() / 20 - brewingTimeMin * 60;
                tooltips.add(new TranslatableComponent("just_enough_keg.jei.tooltip.brewing")
                        .setStyle(Style.EMPTY.withColor(TextColor.parseColor(PUMPKIN_DRINK_CUP_HEX_COLOR)))
                        .append(new TextComponent(brewingTimeMin + ":" + (brewingTimeSec < 10 ? "0" + brewingTimeSec : brewingTimeSec))
                                .withStyle(Style.EMPTY.withBold(true).withColor(TextColor.parseColor(DRINK_BEER_YELLOW)))));
            }
        }
        return tooltips;
    }

    private boolean inCupSlotRange(double mouseX, double mouseY) {
        return mouseX >= 72 && mouseX < 90 && mouseY >= 39 && mouseY <= 57;
    }

    private boolean inTransferBottomRange(double mouseX, double mouseY) {
        return mouseX >= 156 && mouseX < 169 && mouseY >= 50 && mouseY < 63;
    }
}
