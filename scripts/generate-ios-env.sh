#!/bin/sh
# Converte as chaves usadas pelo iOS do `.env` da raiz em
# iosApp/Configuration/Env.xcconfig, incluído pelo Config.xcconfig.
# Rode de novo sempre que mudar o `.env`.
set -eu

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
ENV_FILE="$ROOT/.env"
OUT_FILE="$ROOT/iosApp/Configuration/Env.xcconfig"

if [ ! -f "$ENV_FILE" ]; then
    echo "Sem .env em $ROOT; nada a gerar." >&2
    exit 0
fi

{
    echo "// Gerado por scripts/generate-ios-env.sh a partir do .env. Não edite."
    grep -E '^[[:space:]]*API_BASE_URL[[:space:]]*=' "$ENV_FILE" \
        | sed -E 's/^[[:space:]]*//; s/[[:space:]]*=[[:space:]]*/ = /; s/"//g; s#//#/$()/#g'
} > "$OUT_FILE"

echo "Gerado $OUT_FILE"
