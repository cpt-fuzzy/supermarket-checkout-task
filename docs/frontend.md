# Frontend — TypeScript / Angular 21

## TypeScript
- `strict: true` is enforced — no implicit `any`, no implicit returns, no fallthrough.
- Prefer type inference when obvious; annotate when it aids readability.
- Never use `any`; use `unknown` when the type is genuinely uncertain.
- Indentation: 2 spaces (EditorConfig). Final newline required. No trailing whitespace.

## Angular Components
- **Do NOT** set `standalone: true` in decorators — it is the default in Angular v20+.
- Set `changeDetection: ChangeDetectionStrategy.OnPush` on every component.
- Use `input()` / `output()` functions, not `@Input()` / `@Output()` decorators.
- Use `inject()` for dependency injection, not constructor parameters.
- Host bindings go in the `host` object of `@Component`/`@Directive` — not `@HostBinding`/`@HostListener`.
- Use `NgOptimizedImage` for all static images (not inline base64).
- No `ngClass` — use `[class.foo]="condition"` bindings.
- No `ngStyle` — use `[style.color]="value"` bindings.
- External template/style paths must be relative to the component `.ts` file.
- Keep components small and focused on a single responsibility.
- Prefer inline templates for small components.
- Prefer Reactive forms instead of Template-driven ones.

## Templates
- Use native control flow: `@if`, `@for`, `@switch` — not `*ngIf`, `*ngFor`, `*ngSwitch`.
- Keep templates free of complex logic; move logic to the component class.
- Use the `async` pipe for observables in templates.
- Do not assume globals like `new Date()` are available.

## State & Signals
- Use `signal()` for local state, `computed()` for derived state.
- Use `.set()` or `.update()` on signals — never `.mutate()`.
- Keep state transformations pure and predictable.
- Lazy-load feature routes; don't eagerly load what isn't needed at startup.

## Services
- One responsibility per service.
- `providedIn: 'root'` for singleton services.
- Use `inject()` for dependency injection, not constructor parameters.

## Accessibility
- All components must pass AXE checks.
- Follow WCAG AA: focus management, color contrast, correct ARIA attributes.

## Testing (Vitest + Angular TestBed)
- Tests use Vitest globals (`describe`, `it`, `expect`, `beforeEach`) — no import needed (configured via `tsconfig.spec.json`).
- Use `TestBed.configureTestingModule({ imports: [MyComponent] })` for component tests.
- DOM assertions via `fixture.nativeElement` queries.

## Linting & Formatting
- **ESLint** enforces Angular, TypeScript, and template accessibility rules. Run with `bun run lint`.
- **Prettier** handles formatting (100-char width, single quotes, Angular HTML parser). Run with `bunx prettier --write .`.
- `eslint-config-prettier` disables ESLint formatting rules to avoid conflicts.
- Both are checked in CI (`frontend-ci.yml`).
