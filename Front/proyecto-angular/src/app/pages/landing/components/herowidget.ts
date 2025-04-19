import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';

@Component({
    selector: 'hero-widget',
    imports: [ButtonModule, RippleModule],
    template: `
        <div
            id="hero"
            class="flex flex-col pt-6 px-6 lg:px-20 overflow-hidden"
            style="background: linear-gradient(0deg, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.2)), radial-gradient(77.36% 256.97% at 77.36% 57.52%, rgb(238, 239, 175) 0%, rgb(195, 227, 250) 100%); clip-path: ellipse(150% 87% at 93% 13%)"
        >
            <div class="mx-6 md:mx-20 mt-0 md:mt-40">
                <h1 class="text-6xl font-bold text-gray-900 leading-tight"><span class="font-light block">Bienvenido a VitalData</span>Análisis y Estadísticas en Salud Pública</h1>
                <p class="font-normal text-2xl leading-normal md:mt-4 text-gray-700">
                Nos especializamos en el análisis integral de datos relacionados con la salud pública. A través del procesamiento y visualización de información confiable, brindamos herramientas estadísticas que apoyan la toma de decisiones, la formulación de políticas sanitarias y la identificación de tendencias clave en enfermedades, atención médica, prevención y otros factores determinantes de la salud.
                </p>
                <p class="font-normal text-2xl leading-normal md:mt-4 text-gray-700">Explora nuestros informes interactivos, gráficos dinámicos y reportes especializados para comprender mejor el panorama actual de la salud pública en tu región y más allá.
                </p>
                <button pButton pRipple [rounded]="true" type="button" label="Get Started" class="!text-xl mt-8 !px-4"></button>
            </div>
            <div class="flex justify-center md:justify-end">
                <img src="img/img_salud.png" alt="Imagen de salud" class="max-w-[600px] h-auto" />
            </div>
        </div>
    `
})
export class HeroWidget {}
